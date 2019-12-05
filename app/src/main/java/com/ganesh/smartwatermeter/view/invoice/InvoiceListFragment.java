package com.ganesh.smartwatermeter.view.invoice;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.ganesh.smartwatermeter.R;
import com.ganesh.smartwatermeter.common.base.BaseFragment;
import com.ganesh.smartwatermeter.common.base.BaseNavigator;
import com.ganesh.smartwatermeter.common.base.GenericAdapter;
import com.ganesh.smartwatermeter.common.utils.Constants;
import com.ganesh.smartwatermeter.common.utils.Utils;
import com.ganesh.smartwatermeter.databinding.FragmentInvoiceListBinding;
import com.ganesh.smartwatermeter.databinding.ItemInvoiceBinding;
import com.ganesh.smartwatermeter.model.InvoiceDetailModel;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import pub.devrel.easypermissions.EasyPermissions;

public class InvoiceListFragment extends BaseFragment<FragmentInvoiceListBinding, InvoiceViewModel> implements InvoiceNavigator, EasyPermissions.PermissionCallbacks {

    private static final String TAG = InvoiceListFragment.class.getSimpleName();

    public static InvoiceListFragment newInstance() {
        return new InvoiceListFragment();
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_invoice_list;
    }

    @Override
    public void onBinding() {
        GenericAdapter<InvoiceDetailModel, ItemInvoiceBinding> adapter = new GenericAdapter<InvoiceDetailModel, ItemInvoiceBinding>(mContext) {
            @Override
            public int getLayoutId() {
                return R.layout.item_invoice;
            }

            @Override
            public void onBindView(ItemInvoiceBinding binding, InvoiceDetailModel item, int position) {
                binding.setModel(item);
                binding.setViewModel(mViewModel);
            }
        };
        mBinding.rvInvoice.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvInvoice.setAdapter(adapter);

        mViewModel.getInvoiceModels().observe(this, new Observer<ObservableArrayList<InvoiceDetailModel>>() {
            @Override
            public void onChanged(ObservableArrayList<InvoiceDetailModel> invoiceDetailModels) {
                adapter.updateData(invoiceDetailModels);
            }
        });

        mViewModel.getInvoiceList();
    }

    @Override
    public Class getViewModel() {
        return InvoiceViewModel.class;
    }

    @Override
    public BaseNavigator getNavigatorReference() {
        return this;
    }

    @Override
    public String getScreenName() {
        return TAG;
    }

    @Override
    public void onError(String errorMessage) {
        Utils.getInstance().showErrorMsg(mContext, errorMessage);
    }

    @Override
    public void onNoInternetConnection() {
        Utils.getInstance().showNoInternetMsg(mContext);
    }

    @Override
    public void askPermission(String[] permission) {
        EasyPermissions.requestPermissions(this, getString(R.string.read_storage), Constants.PermissionRquestCode.STORAGE, permission);
    }

    @Override
    public void oniInvoiceDownloaded(File file) {
        showConfirmationDialog("Invoice Downloaded", "Invoice store in\n" + file.getAbsolutePath()+"\nDo you want to open?", Constants.DialogRequestCode.OPEN_INVOICE_PDF);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        mViewModel.downloadInvoice();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    @Override
    protected void onConfirmationDialogPositiveButton(int requestCode) {

        switch (requestCode) {
            case Constants.DialogRequestCode.OPEN_INVOICE_PDF:
                onClearConfirmationDialogPositiveButton();

                Uri apkURI = FileProvider.getUriForFile(
                        mContext,
                        mContext.getApplicationContext()
                                .getPackageName() + ".provider", mViewModel.getCurrentDownloadedFile());

                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                pdfOpenintent.setDataAndType(apkURI, "application/pdf");
                pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                try {
                    startActivity(pdfOpenintent);
                }
                catch (ActivityNotFoundException e) {
                    onError("PDF reader not found");
                }
                break;


        }


    }
}
