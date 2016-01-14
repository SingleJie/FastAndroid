package com.wenjackp.android.lib.util;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * 资源反射类
 *
 * @author Single
 * @version 1.2
 */
public final class ResMappingUtil {

    public static final String DRAWABLE = "drawable";
    public static final String LAYOUT = "layout";
    public static final String XML = "xml";
    public static final String VALUES = "values";
    public static final String RAW = "raw";
    public static final String COLOR = "color";
    public static final String ANIM = "anim";
    public static final String ID = "id";
    public static final String STRING = "string";
    public static final String ARRAY = "array";
    public static final String STYLEABLE = "styleable";
    public static final String DIMEN = "dimen";
    public static final String ATTR = "attr";
    public static final String BOOL = "bool";
    public static final String INTEGER = "integer";

    private Context mContext;

    public ResMappingUtil(Context mContext) {
        this.mContext = mContext;
    }

    public int getAllResourceId(String resourceName, String resourceType) {
        int resourceId = 0;

        try {
            resourceId = mContext.getResources().getIdentifier(resourceName, resourceType, mContext.getPackageName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resourceId;
    }

    public static int getAllResourceId(Context mContext, String resourceName, String resourceType) {
        int resourceId = 0;

        try {
            resourceId = mContext.getResources().getIdentifier(resourceName, resourceType, mContext.getPackageName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resourceId;
    }

    public int getDrawableId(String name) {
        return getAllResourceId(name, DRAWABLE);
    }

    public int getLayoutId(String name) {
        return getAllResourceId(name, LAYOUT);
    }

    public int getAnimationId(String name) {
        return getAllResourceId(name, ANIM);
    }

    public int getStyleableId(String name) {
        return getAllResourceId(name, STYLEABLE);
    }

    public static int getStyleableId(Context mContext, String name) {
        return getAllResourceId(mContext, name, STYLEABLE);
    }

    public int getColorId(String name) {
        return getAllResourceId(name, COLOR);
    }


    public static int getColorId(Context mContext, String name) {
        return getAllResourceId(mContext, name, COLOR);
    }

    public int getControlsId(String name) {
        return getAllResourceId(name, ID);
    }

    public int getStringId(String name) {
        return getAllResourceId(name, STRING);
    }

    public int getDimenId(String name) {
        return getAllResourceId(name, DIMEN);
    }

    public static int getDimenId(Context mContext, String name) {
        return getAllResourceId(mContext, name, DIMEN);
    }

    public int getAttrId(String name) {
        return getAllResourceId(name, ATTR);
    }

    public static int getAttrId(Context mContext, String name) {
        return getAllResourceId(mContext, name, ATTR);
    }


    public int getBoolId(String name) {
        return getAllResourceId(name, BOOL);
    }

    public static int getBoolId(Context mContext, String name) {
        return getAllResourceId(mContext, name, BOOL);
    }

    public int getIntegerId(String name) {
        return getAllResourceId(name, INTEGER);
    }

    public static int getIntegerId(Context mContext, String name) {
        return getAllResourceId(mContext, name, INTEGER);
    }


    public int[] getIdsByName(String name) {
        try {
            //use reflection to access the resource class
            Field[] fields2 = Class.forName(mContext.getPackageName() + ".R$styleable").getFields();

            //browse all fields
            for (Field f : fields2) {
                //pick matching field
                if (f.getName().equals(name)) {
                    //return as int array
                    int[] ret = (int[]) f.get(null);
                    return ret;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static int[] getIdsByName(Context mContext, String name) {
        try {
            //use reflection to access the resource class
            Field[] fields2 = Class.forName(mContext.getPackageName() + ".R$styleable").getFields();

            //browse all fields
            for (Field f : fields2) {
                //pick matching field
                if (f.getName().equals(name)) {
                    //return as int array
                    int[] ret = (int[]) f.get(null);
                    return ret;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


}
