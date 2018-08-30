// IOnNewBookArrivedListener.aidl
package com.example.admin.ipcbyaidl;


import com.example.admin.ipcbyaidl.Book;
interface IOnNewBookArrivedListener {

  void onNewBookArrived(in Book newBook);
}
