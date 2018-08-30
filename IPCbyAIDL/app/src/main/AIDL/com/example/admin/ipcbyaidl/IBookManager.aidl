// IBookManager.aidl
package com.example.admin.ipcbyaidl;

import com.example.admin.ipcbyaidl.Book;
import com.example.admin.ipcbyaidl.IOnNewBookArrivedListener;
interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);

}
