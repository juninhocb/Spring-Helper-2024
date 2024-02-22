package com.carlosjr.messagemodulith.product;

public enum ProductType {
    TYPE_A, TYPE_B, TYPE_C;
    public static ProductType getTypeFromChar(char c){
        switch (c){
            case 'a':
                return TYPE_A;
            case 'b':
                return TYPE_B;
            case 'c':
                return TYPE_C;
            default:
                throw new IllegalArgumentException("Type " + c + " not a valid char");
        }
    }
}
