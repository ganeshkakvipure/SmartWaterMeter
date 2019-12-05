package com.ganesh.smartwatermeter.view.invoice;

import com.ganesh.smartwatermeter.common.base.BaseNavigator;

import java.io.File;

public interface InvoiceNavigator extends BaseNavigator {

     void askPermission(String[] permission);
     void oniInvoiceDownloaded(File file);
}
