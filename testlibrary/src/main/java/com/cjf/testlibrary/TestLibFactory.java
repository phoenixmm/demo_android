package com.cjf.testlibrary;


public class TestLibFactory {
    public int var1 = 1;
    public String var2 = "aaaa ";
    public static StaticInner si = new StaticInner() {
        @Override
        public void method2() {
//            var2 = "3333";
            String var3 = "";
        }
    };

    public class NonStaticInner {
        public void method1() {
            var1 = 3;
        }
    }

    public static class StaticInner {
        public void method2() {
//            var2 = "2222";
        }
    }

    public void method3() {

        final String b = new String("9999");
        new StaticInner() {
            @Override
            public void method2() {
                var2 = "1111" + b.toString();
                String var3 = var2 + b.toString() + var2;
            }
        };
    }

}