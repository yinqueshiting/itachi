package com.example.itachi.config.exception;

public class DebugTest {
    private String string ;

    public String change(){
        string = new String("123");
        return string;
    }

    public static void main(String[] args) {
        DebugTest debugTest =new DebugTest();
        System.out.println(debugTest.change());
        System.out.println("13");
    }
}
