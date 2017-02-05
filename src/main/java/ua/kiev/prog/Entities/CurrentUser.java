package ua.kiev.prog.Entities;

import ua.kiev.prog.Entities.UserContent.User;

// Double Checked Locking & volatile
public class CurrentUser {
    private static volatile User instance; //синхронизирует операцию записи/чтения, гарантирует что
                                                        //работа ведется с оригиналом поля, а не кеш копии

    public static User getInstance() {
        User localInstance = instance;
        if (localInstance == null) {
            synchronized (CurrentUser.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new User();
                }
            }
        }
        return localInstance;
    }

    public void check() {
        System.out.println("Advanced Hello!");
    }
}