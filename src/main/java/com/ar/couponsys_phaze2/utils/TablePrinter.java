package com.ar.couponsys_phaze2.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TablePrinter {
    public static List<String> noShowFields = new ArrayList(List.of());

    public TablePrinter() {
    }

    public static void print(Object item) {
        if (item != null) {
            if (item.getClass().isPrimitive()) {
                System.out.println(item);
            } else if (item instanceof Collection) {
                print(List.copyOf((Collection)item));
            } else {
                print(List.of(item));
            }
        } else {
            System.out.println("null");
        }

    }

    public static void print(List<?> list) {
        if (list.isEmpty()) {
            System.out.println("empty collection");
        } else {
            int index;
            for(index = 0; index < list.size() && list.get(index) == null; ++index) {
            }

            if (index == list.size()) {
                System.out.println("collection of nulls");
            }

            Class<?> itemClass = list.get(index).getClass();
            List<List<String>> columns = new ArrayList();
            List<String> columnHeaders = new ArrayList();
            List<Integer> columnWidths = new ArrayList();
            Method[] methods = itemClass.getDeclaredMethods();
            Method[] var7 = methods;
            int rowIndex = methods.length;

            int columnIndex;
            for(columnIndex = 0; columnIndex < rowIndex; ++columnIndex) {
                Method method = var7[columnIndex];
                String methodName = method.getName();
                if (methodName.startsWith("get")) {
                    methodName = methodName.substring(3);
                    if (!methodName.contains("$") && !methodName.contains("Hibernate") && !noShowFields.contains(methodName.toLowerCase())) {
                        List<String> columnStrings = getMethodStringValues(method, list);
                        if (!columnStrings.isEmpty()) {
                            int columnWidth = Math.max(maxWidth(columnStrings), methodName.length());
                            columnHeaders.add(methodName);
                            columns.add(columnStrings);
                            columnWidths.add(columnWidth);
                        }
                    }
                }
            }

            int allWidth = 0;
            System.out.print("  ");

            for(rowIndex = 0; rowIndex < columnHeaders.size(); ++rowIndex) {
                String header = (String)columnHeaders.get(rowIndex);
                int columnWidth = (Integer)columnWidths.get(rowIndex);
                allWidth += columnWidth;
                System.out.print(padSpace(header, columnWidth));
                if (rowIndex < columnHeaders.size() - 1) {
                    System.out.print(" | ");
                    allWidth += 3;
                }
            }

            System.out.println();
            System.out.print("  ");
            System.out.println("-".repeat(allWidth));

            for(rowIndex = 0; rowIndex < list.size(); ++rowIndex) {
                System.out.print("  ");

                for(columnIndex = 0; columnIndex < columns.size(); ++columnIndex) {
                    String value = (String)((List)columns.get(columnIndex)).get(rowIndex);
                    int columnWidth = (Integer)columnWidths.get(columnIndex);
                    System.out.print(padSpace(value, columnWidth));
                    if (columnIndex < columns.size() - 1) {
                        System.out.print(" | ");
                    }
                }

                System.out.println();
            }

            System.out.println("  " + "-".repeat(allWidth));
            System.out.println();
        }
    }

    private static List<String> getMethodStringValues(Method method, List<?> objects) {
        List<String> values = new ArrayList();
        if (Modifier.isStatic(method.getModifiers())) {
            return values;
        } else {
            method.setAccessible(true);
            Iterator var3 = objects.iterator();

            while(var3.hasNext()) {
                Object object = var3.next();
                if (object == null) {
                    values.add("null");
                } else {
                    try {
                        Object objectField = method.invoke(object, (Object[])null);
                        if (objectField == null) {
                            values.add("null");
                        } else {
                            values.add(objectField.toString());
                        }
                    } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var6) {
                    }
                }
            }

            return values;
        }
    }

    private static int maxWidth(List<String> ls) {
        int width = 0;

        String string;
        for(Iterator var2 = ls.iterator(); var2.hasNext(); width = Math.max(string.length(), width)) {
            string = (String)var2.next();
        }

        return width;
    }

    private static String padSpace(String str, int length) {
        StringBuilder builder = new StringBuilder(str);

        while(builder.length() <= length - 2) {
            builder.append(' ');
            builder.insert(0, ' ');
        }

        if (builder.length() < length) {
            builder.insert(0, ' ');
        }

        return builder.toString();
    }
}
