package sr.unasat.thewishapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import sr.unasat.thewishapp.R;
import sr.unasat.thewishapp.models.Products;
import sr.unasat.thewishapp.models.Users;

import static android.content.ContentValues.TAG;

public class WishAppDAO extends SQLiteOpenHelper {

    private final Context context;

    private static final String DATABASE_NAME = "wishStore.db";
    private static final int DATABASE_VERSION = 5;

    public static final String USERS_TABLE = "users";
    public static final String USERS_ID = "user_id";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";
    public static final String USERS_FIRSTNAME = "firstname";
    public static final String USERS_LASTNAME = "lastname";
    public static final String USERS_ADDRESS = "address";
    public static final String USERS_CONTACT = "contactnumber";
    public static final String USERS_ACCOUNT = "accountnumber";
    public static final String USERS_SALDO = "saldo";
    public static final String USERS_BIRTHDATE = "birthdate";
    public static final String USERS_GENDER = "gender_id";

    public static final String RECEIVERS_TABLE = "receivers";
    public static final String RECEIVERS_ID = "receiver_id";
    public static final String RECEIVERS_FIRSTNAME = "firstname";
    public static final String RECEIVERS_LASTNAME = "lastname";
    public static final String RECEIVERS_ADDRESS = "address";
    public static final String RECEIVERS_CONTACT = "contactnumber";

    public static final String PRODUCTS_TABLE = "products";
    public static final String PRODUCTS_ID = "product_id";
    public static final String PRODUCT_PIC = "picture";
    public static final String PRODUCTS_NAME = "name";
    public static final String PRODUCTS_PRICE = "price";
    public static final String PRODUCTS_STOCK = "stock";
    public static final String PRODUCTS_SALES = "sales";
    public static final String PRODUCTS_MAGNITUDE = "magnitude";
    public static final String PRODUCTS_BRANDS = "brands";
    public static final String PRODUCTS_CATEGORY_ID = "category_id";

    public static final String CATEGORY_TABLE = "category";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_TIMES_VIEWED = "times_viewed";

    public static final String GENDER_TABLE = "gender";
    public static final String GENDER_ID = "gender_id";
    public static final String GENDER = "gender";

    public static final String SALES_TABLE = "sales";
    public static final String SALES_USER_ID = "user_id";
    public static final String SALES_PRODUCT_ID = "product_id";
    public static final String SALES_RECEIVER_ID = "receiver_id";
    public static final String SALES_ORDER_DATE = "order_date";

    public static final String CART_TABLE = "cart";
    public static final String CART_PRODUCT_ID = "product_id";
    public static final String CART_IMAGE = "image_resource";
    public static final String CART_NAME = "name";
    public static final String CART_MAGNITUDE = "magnitude";
    public static final String CART_BRAND = "brand";
    public static final String CART_PRICE = "price";

    public static final String GIFT_TABLE = "gift";
    public static final String GIFT_PRODUCT_ID = "product_id";
    public static final String GIFT_IMAGE = "image_resource";
    public static final String GIFT_NAME = "name";
    public static final String GIFT_MAGNITUDE = "magnitude";
    public static final String GIFT_BRAND = "brand";
    public static final String GIFT_PRICE = "price";

    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE users (\n" +
            "    user_id         INTEGER NOT NULL PRIMARY KEY,\n" +
            "    firstname       STRING NOT NULL,\n" +
            "    lastname        STRING NOT NULL,\n" +
            "    username        STRING NOT NULL,\n" +
            "    password        STRING NOT NULL,\n" +
            "    address         STRING NOT NULL,\n" +
            "    contactnumber   INTEGER NOT NULL,\n" +
            "    birthdate       DATE,\n" +
            "    accountnumber   INTEGER NOT NULL UNIQUE,\n" +
            "    saldo           REAL ,\n" +
            "    gender_id       INTEGER NOT NULL,\n" +
            "FOREIGN KEY (gender_id)\n" +
            "       REFERENCES gender (gender_id)\n" +
            ")";

    private static final String SQL_CREATE_RECEIVERS_TABLE = "CREATE TABLE receivers (\n" +
            "    receiver_id    INTEGER NOT NULL PRIMARY KEY,\n" +
            "    firstname      STRING NOT NULL, \n" +
            "    lastname       STRING NOT NULL,\n" +
            "    contactnumber  INTEGER NOT NULL,\n" +
            "    address        STRING NOT NULL\n" +
            ")";

    private static final String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE products (\n" +
            "    product_id   INTEGER NOT NULL PRIMARY KEY,\n" +
            "    picture      BLOB ,\n" +
            "    name         STRING NOT NULL,\n" +
            "    price        REAL,\n" +
            "    stock        INTEGER NOT NULL,\n" +
            "    sales        INTEGER NOT NULL,\n" +
            "    magnitude    STRING,\n" +
            "    brands       STRING NOT NULL, \n" +
            "    category_id  INTEGER NOT NULL,\n" +
            "FOREIGN KEY (category_id)\n" +
            "       REFERENCES category (category_id)\n" +
            ")";

    private static final String SQL_CREATE_CATEGORY_TABLE = "CREATE TABLE category (\n" +
            "    category_id         INTEGER NOT NULL PRIMARY KEY,\n" +
            "    category_name       STRING NOT NULL,\n" +
            "    times_viewed        INTEGER NOT NULL\n" +
            ")";

    private static final String SQL_CREATE_GENDER_TABLE = "CREATE TABLE gender (\n" +
            "    gender_id  INTEGER NOT NULL PRIMARY KEY,\n" +
            "    gender     STRING NOT NULL\n" +
            ")";

    private static final String SQL_CREATE_SALES_TABLE = "CREATE TABLE sales (\n" +
            "    user_id      INTEGER NOT NULL,\n" +
            "    product_id   INTEGER NOT NULL,\n" +
            "    receiver_id  INTEGER,\n" +
            "    order_date   DATE NOT NULL,\n" +
            "FOREIGN KEY (user_id)\n" +
            "       REFERENCES users (user_id),\n" +
            "FOREIGN KEY (product_id)\n" +
            "       REFERENCES products (product_id),\n" +
            "FOREIGN KEY (receiver_id)\n" +
            "       REFERENCES receivers (receiver_id)\n" +
            ")";

    public WishAppDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        //setDefaultCredentials();
        //deleteAll();
        //setDefaultCredentialsCategory();
        //setDefaultCredentialsProducts();

    }

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS ";
    public static final String SQL_COUNT_TOTAL = "select sum(price) from cart";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_RECEIVERS_TABLE);
//        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
        updateDatabase(db, 2, DATABASE_VERSION);
        db.execSQL(SQL_CREATE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_GENDER_TABLE);
        db.execSQL(SQL_CREATE_SALES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(SQL_DROP_TABLE + USERS_TABLE);
//        db.execSQL(SQL_DROP_TABLE + RECEIVERS_TABLE);
//        db.execSQL(SQL_DROP_TABLE + PRODUCTS_TABLE);
//        db.execSQL(SQL_DROP_TABLE + CATEGORY_TABLE);
//        db.execSQL(SQL_DROP_TABLE + GENDER_TABLE);
//        db.execSQL(SQL_DROP_TABLE + SALES_TABLE);
        updateDatabase(db, oldVersion, newVersion);

    }

    public void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 3){
            db.execSQL(SQL_DROP_TABLE + PRODUCTS_TABLE);
            db.execSQL("CREATE TABLE products (\n" +
                    "    product_id   INTEGER NOT NULL PRIMARY KEY,\n" +
                    "    picture      INTEGER ,\n" +
                    "    name         STRING NOT NULL,\n" +
                    "    price        REAL,\n" +
                    "    stock        INTEGER NOT NULL,\n" +
                    "    sales        INTEGER NOT NULL,\n" +
                    "    magnitude    STRING,\n" +
                    "    brands       STRING NOT NULL, \n" +
                    "    category_id  INTEGER NOT NULL,\n" +
                    "FOREIGN KEY (category_id)\n" +
                    "       REFERENCES category (category_id)\n" +
                    ")");
        }if(oldVersion < 4){
            db.execSQL("CREATE TABLE cart (\n" +
                    "    product_id   INTEGER NOT NULL ,\n" +
                    "    image_resource      INTEGER ,\n" +
                    "    name         STRING NOT NULL,\n" +
                    "    magnitude        STRING,\n" +
                    "    brand        STRING NOT NULL,\n" +
                    "    price        REAL NOT NULL,\n" +
                    "FOREIGN KEY (product_id)\n" +
                    "       REFERENCES products (product_id)\n" +
                    ")");
        }if(oldVersion < 5){
            db.execSQL("CREATE TABLE gift (\n" +
                    "    product_id   INTEGER NOT NULL ,\n" +
                    "    image_resource      INTEGER ,\n" +
                    "    name         STRING NOT NULL,\n" +
                    "    magnitude        STRING,\n" +
                    "    brand        STRING NOT NULL,\n" +
                    "    price        REAL NOT NULL,\n" +
                    "FOREIGN KEY (product_id)\n" +
                    "       REFERENCES products (product_id)\n" +
                    ")");
        }
    }

    public long insertOneRecord(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = db.insert(tableName, null, contentValues);
        db.close();
        return rowId;
    }

    public ArrayList<Double> totalSpend(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Double> amount = new ArrayList<>();
        double price;
        Cursor cursor = db.rawQuery(SQL_COUNT_TOTAL, null, null);
        if(cursor.moveToFirst()){
            do {
                price = cursor.getDouble(0);
                amount.add(price);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return amount;
    }

    public ArrayList<Products> productsArrayListGift() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> storeProducts = new ArrayList<>();
        String[] columns = {GIFT_PRODUCT_ID, GIFT_IMAGE, GIFT_NAME, GIFT_PRICE, GIFT_MAGNITUDE, GIFT_BRAND};
//        String whereClause = String.format("%s = ?", PRODUCTS_CATEGORY_ID);
//        String[] whereArgs = {categoryId};
        Cursor cursor = db.query(GIFT_TABLE, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(0);
                int imageResource = Integer.parseInt(cursor.getString(1));
                String name = cursor.getString(2);
                Double price = Double.parseDouble(cursor.getString(3));
                String magnitude = cursor.getString(4);
                String brand = cursor.getString(5);
                storeProducts.add(new Products(productId, imageResource, name, price, magnitude, brand));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public ArrayList<Products> productsArrayListCart() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> storeProducts = new ArrayList<>();
        String[] columns = {CART_PRODUCT_ID, CART_IMAGE, CART_NAME, CART_PRICE, CART_MAGNITUDE, CART_BRAND};
//        String whereClause = String.format("%s = ?", PRODUCTS_CATEGORY_ID);
//        String[] whereArgs = {categoryId};
        Cursor cursor = db.query(CART_TABLE, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(0);
                int imageResource = Integer.parseInt(cursor.getString(1));
                String name = cursor.getString(2);
                Double price = Double.parseDouble(cursor.getString(3));
                String magnitude = cursor.getString(4);
                String brand = cursor.getString(5);
                storeProducts.add(new Products(productId, imageResource, name, price, magnitude, brand));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public ArrayList<Products> productsArrayList(String categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> storeProducts = new ArrayList<>();
        String[] columns = {PRODUCTS_ID, PRODUCT_PIC, PRODUCTS_NAME, PRODUCTS_PRICE, PRODUCTS_MAGNITUDE, PRODUCTS_BRANDS};
        String whereClause = String.format("%s = ?", PRODUCTS_CATEGORY_ID);
        String[] whereArgs = {categoryId};
        Cursor cursor = db.query(PRODUCTS_TABLE, columns, whereClause, whereArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(0);
                int imageResource = Integer.parseInt(cursor.getString(1));
                String name = cursor.getString(2);
                Double price = Double.parseDouble(cursor.getString(3));
                String magnitude = cursor.getString(4);
                String brand = cursor.getString(5);
                storeProducts.add(new Products(productId, imageResource, name, price, magnitude, brand));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public ArrayList<Products> bestsellers(String bestsellersStartLimit) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> storeProducts = new ArrayList<>();
        String[] columns = {PRODUCTS_ID, PRODUCT_PIC, PRODUCTS_NAME, PRODUCTS_PRICE, PRODUCTS_MAGNITUDE, PRODUCTS_BRANDS};
        String whereClause = String.format("%s > ?", PRODUCTS_SALES);
        String[] whereArgs = {bestsellersStartLimit};
        Cursor cursor = db.query(PRODUCTS_TABLE, columns, whereClause, whereArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int product_id = cursor.getInt(0);
                int imageResource = Integer.parseInt(cursor.getString(1));
                String name = cursor.getString(2);
                Double price = Double.parseDouble(cursor.getString(3));
                String magnitude = cursor.getString(4);
                String brand = cursor.getString(5);
                storeProducts.add(new Products(product_id, imageResource, name, price, magnitude, brand));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeProducts;
    }

    public int updateRecord(String categoryId) {
        SQLiteDatabase db = getWritableDatabase();
        int effectedRows = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_TIMES_VIEWED, CATEGORY_TIMES_VIEWED+1);
        String whereClause = String.format("%s = ?", CATEGORY_ID);
        String[] whereArgs = {categoryId};
        effectedRows = db.update(CATEGORY_TABLE, contentValues, whereClause, whereArgs);
        if(effectedRows>0){
            System.out.println("Updated");
        }else{
            System.out.println("Something went wrong");
        }
        return effectedRows;
    }

    public void setDefaultCredentials() {
        //Set default
        ContentValues contentValues = new ContentValues();
        contentValues.put(GENDER_ID, 1);
        contentValues.put(GENDER, "male");
        insertOneRecord(GENDER_TABLE, contentValues);

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(GENDER_ID, 2);
        contentValues1.put(GENDER, "female");
        insertOneRecord(GENDER_TABLE, contentValues1);
    }

    public Boolean findRecords(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ? AND %s = ?", USERS_USERNAME, USERS_PASSWORD);
        String[] whereArgs = {username, password};
        String[] columns = {USERS_USERNAME, USERS_PASSWORD};
        cursor = db.query(USERS_TABLE, columns, whereClause, whereArgs ,null, null, null);
        return cursor.getCount() > 0;
    }

    public boolean findRecords1(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?" , USERS_USERNAME);
        String[] whereArgs = {username};
        String[] columns = {USERS_USERNAME};
        cursor = db.query(USERS_TABLE, columns, whereClause, whereArgs ,null, null, null);
        return cursor.getCount() > 0;
    }

    public void findRecords2(String username, TextView textView, int i) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?" , USERS_USERNAME);
        String[] whereArgs = {username};
        cursor = db.query(USERS_TABLE, null, whereClause, whereArgs ,null, null, null);
        while (cursor.moveToNext()){
            textView.setText(cursor.getString(i));;
        }

    }
    public List<Double> findRecordsSaldo(String username){
        SQLiteDatabase db=getReadableDatabase();
        List<Double>saldo=new ArrayList<>();
        Cursor cursor;
        String whereClause=String.format("%s=?",USERS_USERNAME);
        String[] whereArgs= {username};
        String[]columns= {USERS_SALDO};
        cursor = db.query(USERS_TABLE, columns, whereClause, whereArgs ,null, null, null);
        double  number;
        if (cursor.moveToFirst()) {
            do {
               number=cursor.getDouble(0);
               saldo.add(number);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return saldo;
    }

    public void findRecordsGender(String username, TextView textView) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        String whereClause = String.format("%s = ?" , USERS_USERNAME);
        String[] whereArgs = {username};
        cursor = db.query(USERS_TABLE, null, whereClause, whereArgs ,null, null, null);
        while (cursor.moveToNext()){
            if(cursor.getString(10).equals("1")){
                textView.setText("Male");
            }else{
                textView.setText("Female");
            }
        }
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + PRODUCTS_TABLE);
        db.close();
    }

    public int deleteRecord(String table, String whereColumn, String id) {
        SQLiteDatabase db = getWritableDatabase();
        int effectedRows = 0;
        String whereClause = String.format("%s = ?", whereColumn);
        String[] whereArgs = {id};
        effectedRows = db.delete(table, whereClause, whereArgs);
        return effectedRows;
    }

    public int updateRecord(ContentValues contentValues, String username) {
        SQLiteDatabase db = getWritableDatabase();
        int effectedRows = 0;
        String whereClause = String.format("%s = ?", USERS_USERNAME);
        String[] whereArgs = {username};
        effectedRows = db.update(USERS_TABLE, contentValues, whereClause, whereArgs);
        return effectedRows;
    }

    public void setDefaultCredentialsProducts() {
        //Set default
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCTS_ID, 1);
        contentValues.put(PRODUCT_PIC, R.drawable.joggingbroek_heren_1_1565016738); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 350);
        contentValues.put(PRODUCTS_STOCK, 357);
        contentValues.put(PRODUCTS_SALES, 234);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Calvin Klein");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 2);
        contentValues.put(PRODUCT_PIC, R.drawable.pic2); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 375);
        contentValues.put(PRODUCTS_STOCK, 347);
        contentValues.put(PRODUCTS_SALES, 235);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Supreme");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 3);
        contentValues.put(PRODUCT_PIC, R.drawable.pic4); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 500);
        contentValues.put(PRODUCTS_STOCK, 245);
        contentValues.put(PRODUCTS_SALES, 567);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "New Choice");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 4);
        contentValues.put(PRODUCT_PIC, R.drawable.pic5); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 267);
        contentValues.put(PRODUCTS_STOCK, 123);
        contentValues.put(PRODUCTS_SALES, 185);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Givenchy");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 5);
        contentValues.put(PRODUCT_PIC, R.drawable.pic6); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 185);
        contentValues.put(PRODUCTS_STOCK, 267);
        contentValues.put(PRODUCTS_SALES, 229);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Balenciaga");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 6);
        contentValues.put(PRODUCT_PIC, R.drawable.pic7); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 326);
        contentValues.put(PRODUCTS_STOCK, 267);
        contentValues.put(PRODUCTS_SALES, 229);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Equalite");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 7);
        contentValues.put(PRODUCT_PIC, R.drawable.pic8); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 326);
        contentValues.put(PRODUCTS_STOCK, 267);
        contentValues.put(PRODUCTS_SALES, 229);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 8);
        contentValues.put(PRODUCT_PIC, R.drawable.pic9); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 157);
        contentValues.put(PRODUCTS_STOCK, 162);
        contentValues.put(PRODUCTS_SALES, 253);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "NIKE+");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 9);
        contentValues.put(PRODUCT_PIC, R.drawable.pic10); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 520);
        contentValues.put(PRODUCTS_STOCK, 252);
        contentValues.put(PRODUCTS_SALES, 252);
        contentValues.put(PRODUCTS_MAGNITUDE, "XS");
        contentValues.put(PRODUCTS_BRANDS, "Supreme");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 10);
        contentValues.put(PRODUCT_PIC, R.drawable.pic11); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 326);
        contentValues.put(PRODUCTS_STOCK, 267);
        contentValues.put(PRODUCTS_SALES, 229);
        contentValues.put(PRODUCTS_MAGNITUDE, "XXS");
        contentValues.put(PRODUCTS_BRANDS, "Givenchy");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 11);
        contentValues.put(PRODUCT_PIC, R.drawable.pic12); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 472);
        contentValues.put(PRODUCTS_STOCK, 251);
        contentValues.put(PRODUCTS_SALES, 12);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Adidas");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 12);
        contentValues.put(PRODUCT_PIC, R.drawable.pic13); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 152);
        contentValues.put(PRODUCTS_STOCK, 282);
        contentValues.put(PRODUCTS_SALES, 215);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jack & Jones");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 13);
        contentValues.put(PRODUCT_PIC, R.drawable.pic14); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 231);
        contentValues.put(PRODUCTS_STOCK, 582);
        contentValues.put(PRODUCTS_SALES, 38);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Jack & Jones");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 14);
        contentValues.put(PRODUCT_PIC, R.drawable.pic15); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 142);
        contentValues.put(PRODUCTS_STOCK, 352);
        contentValues.put(PRODUCTS_SALES, 13);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Adidas");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 15);
        contentValues.put(PRODUCT_PIC, R.drawable.pic16); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 221);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 142);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 16);
        contentValues.put(PRODUCT_PIC, R.drawable.pic17); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 100);
        contentValues.put(PRODUCTS_STOCK, 523);
        contentValues.put(PRODUCTS_SALES, 743);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "New Choice");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 17);
        contentValues.put(PRODUCT_PIC, R.drawable.pic18); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 120);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 152);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 18);
        contentValues.put(PRODUCT_PIC, R.drawable.pic19); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 152);
        contentValues.put(PRODUCTS_STOCK, 521);
        contentValues.put(PRODUCTS_SALES, 142);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Legend");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 19);
        contentValues.put(PRODUCT_PIC, R.drawable.pic20); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 182);
        contentValues.put(PRODUCTS_STOCK, 272);
        contentValues.put(PRODUCTS_SALES, 583);
        contentValues.put(PRODUCTS_MAGNITUDE, "XS");
        contentValues.put(PRODUCTS_BRANDS, "Qike");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 20);
        contentValues.put(PRODUCT_PIC, R.drawable.pic21); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 35);
        contentValues.put(PRODUCTS_STOCK, 153);
        contentValues.put(PRODUCTS_SALES, 253);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Levi's");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 21);
        contentValues.put(PRODUCT_PIC, R.drawable.pic22); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 145);
        contentValues.put(PRODUCTS_STOCK, 392);
        contentValues.put(PRODUCTS_SALES, 132);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Only");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 22);
        contentValues.put(PRODUCT_PIC, R.drawable.pic23); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 250);
        contentValues.put(PRODUCTS_STOCK, 142);
        contentValues.put(PRODUCTS_SALES, 150);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Riverso");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 23);
        contentValues.put(PRODUCT_PIC, R.drawable.pic24); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 145);
        contentValues.put(PRODUCTS_STOCK, 293);
        contentValues.put(PRODUCTS_SALES, 230);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Chasin");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

//        contentValues.put(PRODUCTS_ID, 24);
//        contentValues.put(PRODUCT_PIC, R.drawable.pic25); //Picture zette , maar t is op String
//        contentValues.put(PRODUCTS_NAME, "Shorts");
//        contentValues.put(PRODUCTS_PRICE, 155);
//        contentValues.put(PRODUCTS_STOCK, 392);
//        contentValues.put(PRODUCTS_SALES, 105);
//        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
//        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
//        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
//        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 25);
        contentValues.put(PRODUCT_PIC, R.drawable.pic26); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 643);
        contentValues.put(PRODUCTS_STOCK, 593);
        contentValues.put(PRODUCTS_SALES, 483);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Diesel");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 26);
        contentValues.put(PRODUCT_PIC, R.drawable.pic27); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 346);
        contentValues.put(PRODUCTS_STOCK, 124);
        contentValues.put(PRODUCTS_SALES, 457);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 27);
        contentValues.put(PRODUCT_PIC, R.drawable.pic28); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 673);
        contentValues.put(PRODUCTS_STOCK, 123);
        contentValues.put(PRODUCTS_SALES, 421);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Levi's");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 28);
        contentValues.put(PRODUCT_PIC, R.drawable.pic29); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 675);
        contentValues.put(PRODUCTS_STOCK, 153);
        contentValues.put(PRODUCTS_SALES, 352);
        contentValues.put(PRODUCTS_MAGNITUDE, "XXL");
        contentValues.put(PRODUCTS_BRANDS, "Diesel");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 29);
        contentValues.put(PRODUCT_PIC, R.drawable.pic30); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 867);
        contentValues.put(PRODUCTS_STOCK, 387);
        contentValues.put(PRODUCTS_SALES, 975);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 30);
        contentValues.put(PRODUCT_PIC, R.drawable.pic31); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 792);
        contentValues.put(PRODUCTS_STOCK, 253);
        contentValues.put(PRODUCTS_SALES, 1012);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Levi's");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 31);
        contentValues.put(PRODUCT_PIC, R.drawable.pic32); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 1050);
        contentValues.put(PRODUCTS_STOCK, 392);
        contentValues.put(PRODUCTS_SALES, 493);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Forever21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 32);
        contentValues.put(PRODUCT_PIC, R.drawable.pic33); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 632);
        contentValues.put(PRODUCTS_STOCK, 371);
        contentValues.put(PRODUCTS_SALES, 261);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Classic Jeans");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 33);
        contentValues.put(PRODUCT_PIC, R.drawable.pic34); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 852);
        contentValues.put(PRODUCTS_STOCK, 275);
        contentValues.put(PRODUCTS_SALES, 265);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Cars Jeans");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 34);
        contentValues.put(PRODUCT_PIC, R.drawable.pic35); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 836);
        contentValues.put(PRODUCTS_STOCK, 286);
        contentValues.put(PRODUCTS_SALES, 837);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Only");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 35);
        contentValues.put(PRODUCT_PIC, R.drawable.pic36); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 846);
        contentValues.put(PRODUCTS_STOCK, 162);
        contentValues.put(PRODUCTS_SALES, 347);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Diesel");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 36);
        contentValues.put(PRODUCT_PIC, R.drawable.pic37); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 251);
        contentValues.put(PRODUCTS_STOCK, 262);
        contentValues.put(PRODUCTS_SALES, 252);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Levi's");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 37);
        contentValues.put(PRODUCT_PIC, R.drawable.pic38); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 265);
        contentValues.put(PRODUCTS_STOCK, 376);
        contentValues.put(PRODUCTS_SALES, 936);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 38);
        contentValues.put(PRODUCT_PIC, R.drawable.pic39); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 354);
        contentValues.put(PRODUCTS_STOCK, 275);
        contentValues.put(PRODUCTS_SALES, 123);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 39);
        contentValues.put(PRODUCT_PIC, R.drawable.pic40); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 254);
        contentValues.put(PRODUCTS_STOCK, 624);
        contentValues.put(PRODUCTS_SALES, 234);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Forever21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 40);
        contentValues.put(PRODUCT_PIC, R.drawable.pic41); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 372);
        contentValues.put(PRODUCTS_STOCK, 263);
        contentValues.put(PRODUCTS_SALES, 123);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Classic Jeans");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 41);
        contentValues.put(PRODUCT_PIC, R.drawable.pic42); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 431);
        contentValues.put(PRODUCTS_STOCK, 212);
        contentValues.put(PRODUCTS_SALES, 412);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Diesel");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 42);
        contentValues.put(PRODUCT_PIC, R.drawable.pic43); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 459);
        contentValues.put(PRODUCTS_STOCK, 284);
        contentValues.put(PRODUCTS_SALES, 345);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 43);
        contentValues.put(PRODUCT_PIC, R.drawable.pic44); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 352);
        contentValues.put(PRODUCTS_STOCK, 262);
        contentValues.put(PRODUCTS_SALES, 262);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Supreme");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 44);
        contentValues.put(PRODUCT_PIC, R.drawable.pic45); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 251);
        contentValues.put(PRODUCTS_STOCK, 365);
        contentValues.put(PRODUCTS_SALES, 165);
        contentValues.put(PRODUCTS_MAGNITUDE, "XS");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 45);
        contentValues.put(PRODUCT_PIC, R.drawable.pic46); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 356);
        contentValues.put(PRODUCTS_STOCK, 362);
        contentValues.put(PRODUCTS_SALES, 262);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Forever21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 46);
        contentValues.put(PRODUCT_PIC, R.drawable.pic47); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantalon");
        contentValues.put(PRODUCTS_PRICE, 362);
        contentValues.put(PRODUCTS_STOCK, 27);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Cars Jeans");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 47);
        contentValues.put(PRODUCT_PIC, R.drawable.pic48); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 450);
        contentValues.put(PRODUCTS_STOCK, 13);
        contentValues.put(PRODUCTS_SALES, 975);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "BALR.");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 48);
        contentValues.put(PRODUCT_PIC, R.drawable.pic49); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 320);
        contentValues.put(PRODUCTS_STOCK, 163);
        contentValues.put(PRODUCTS_SALES, 483);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "CloudStyle");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 49);
        contentValues.put(PRODUCT_PIC, R.drawable.pic50); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Button Shirt Short Sleeves");
        contentValues.put(PRODUCTS_PRICE, 271);
        contentValues.put(PRODUCTS_STOCK, 321);
        contentValues.put(PRODUCTS_SALES, 361);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Forever21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 50);
        contentValues.put(PRODUCT_PIC, R.drawable.pic51); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 501);
        contentValues.put(PRODUCTS_STOCK, 25);
        contentValues.put(PRODUCTS_SALES, 262);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Adidas");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 51);
        contentValues.put(PRODUCT_PIC, R.drawable.pic52); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 683);
        contentValues.put(PRODUCTS_STOCK, 153);
        contentValues.put(PRODUCTS_SALES, 493);
        contentValues.put(PRODUCTS_MAGNITUDE, "XS");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 52);
        contentValues.put(PRODUCT_PIC, R.drawable.pic53); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 594);
        contentValues.put(PRODUCTS_STOCK, 12);
        contentValues.put(PRODUCTS_SALES, 532);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 53);
        contentValues.put(PRODUCT_PIC, R.drawable.pic54); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 799);
        contentValues.put(PRODUCTS_STOCK, 274);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 54);
        contentValues.put(PRODUCT_PIC, R.drawable.pic55); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 757);
        contentValues.put(PRODUCTS_STOCK, 821);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Ralph Lauren");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 55);
        contentValues.put(PRODUCT_PIC, R.drawable.pic56); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Button Shirt Short Sleeves Floral");
        contentValues.put(PRODUCTS_PRICE, 320);
        contentValues.put(PRODUCTS_STOCK, 173);
        contentValues.put(PRODUCTS_SALES, 14);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 56);
        contentValues.put(PRODUCT_PIC, R.drawable.pic57); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Button Shirt Long Sleeves Floral");
        contentValues.put(PRODUCTS_PRICE, 625);
        contentValues.put(PRODUCTS_STOCK, 272);
        contentValues.put(PRODUCTS_SALES, 273);
        contentValues.put(PRODUCTS_MAGNITUDE, "XS");
        contentValues.put(PRODUCTS_BRANDS, "Forever21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 57);
        contentValues.put(PRODUCT_PIC, R.drawable.pic58); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts Long Sleeves");
        contentValues.put(PRODUCTS_PRICE, 459);
        contentValues.put(PRODUCTS_STOCK, 138);
        contentValues.put(PRODUCTS_SALES, 397);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Under Armor");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 58);
        contentValues.put(PRODUCT_PIC, R.drawable.pic59); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Button Shirt Long Sleeves");
        contentValues.put(PRODUCTS_PRICE, 375);
        contentValues.put(PRODUCTS_STOCK, 281);
        contentValues.put(PRODUCTS_SALES, 272);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Mango");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 59);
        contentValues.put(PRODUCT_PIC, R.drawable.pic60); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Button Shirt Long Sleeves");
        contentValues.put(PRODUCTS_PRICE, 371);
        contentValues.put(PRODUCTS_STOCK, 12);
        contentValues.put(PRODUCTS_SALES, 383);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Eagle");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 60);
        contentValues.put(PRODUCT_PIC, R.drawable.pic61); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Button Shirt Long Sleeves");
        contentValues.put(PRODUCTS_PRICE, 482);
        contentValues.put(PRODUCTS_STOCK, 183);
        contentValues.put(PRODUCTS_SALES, 387);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Forever21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 61);
        contentValues.put(PRODUCT_PIC, R.drawable.pic62); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Button Shirt Long Sleeves");
        contentValues.put(PRODUCTS_PRICE, 105);
        contentValues.put(PRODUCTS_STOCK, 281);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "CloudStyle");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 62);
        contentValues.put(PRODUCT_PIC, R.drawable.pic63); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts Long Sleeves");
        contentValues.put(PRODUCTS_PRICE, 183);
        contentValues.put(PRODUCTS_STOCK, 283);
        contentValues.put(PRODUCTS_SALES, 437);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Under Armor");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 63);
        contentValues.put(PRODUCT_PIC, R.drawable.pic64); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hoodies");
        contentValues.put(PRODUCTS_PRICE, 473);
        contentValues.put(PRODUCTS_STOCK, 532);
        contentValues.put(PRODUCTS_SALES, 429);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Givenchy");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 64);
        contentValues.put(PRODUCT_PIC, R.drawable.pic65); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hoodies");
        contentValues.put(PRODUCTS_PRICE, 577);
        contentValues.put(PRODUCTS_STOCK, 483);
        contentValues.put(PRODUCTS_SALES, 373);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Balenciaga");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 65);
        contentValues.put(PRODUCT_PIC, R.drawable.pic66); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hoodies Floral");
        contentValues.put(PRODUCTS_PRICE, 579);
        contentValues.put(PRODUCTS_STOCK, 37);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 66);
        contentValues.put(PRODUCT_PIC, R.drawable.pic67); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 695);
        contentValues.put(PRODUCTS_STOCK, 25);
        contentValues.put(PRODUCTS_SALES, 473);
        contentValues.put(PRODUCTS_MAGNITUDE, "41");
        contentValues.put(PRODUCTS_BRANDS, "Warning");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 67);
        contentValues.put(PRODUCT_PIC, R.drawable.pic68); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 808);
        contentValues.put(PRODUCTS_STOCK, 362);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "43");
        contentValues.put(PRODUCTS_BRANDS, "ROC");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 68);
        contentValues.put(PRODUCT_PIC, R.drawable.pic69); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 1200);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 324);
        contentValues.put(PRODUCTS_MAGNITUDE, "45");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 69);
        contentValues.put(PRODUCT_PIC, R.drawable.pic70); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 1412);
        contentValues.put(PRODUCTS_STOCK, 394);
        contentValues.put(PRODUCTS_SALES, 923);
        contentValues.put(PRODUCTS_MAGNITUDE, "39");
        contentValues.put(PRODUCTS_BRANDS, "Balenciaga");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 70);
        contentValues.put(PRODUCT_PIC, R.drawable.pic71); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 999);
        contentValues.put(PRODUCTS_STOCK, 59);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "42");
        contentValues.put(PRODUCTS_BRANDS, "Fans");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 71);
        contentValues.put(PRODUCT_PIC, R.drawable.pic72); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 893);
        contentValues.put(PRODUCTS_STOCK, 953);
        contentValues.put(PRODUCTS_SALES, 945);
        contentValues.put(PRODUCTS_MAGNITUDE, "44");
        contentValues.put(PRODUCTS_BRANDS, "Sketchers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 72);
        contentValues.put(PRODUCT_PIC, R.drawable.pic73); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 801);
        contentValues.put(PRODUCTS_STOCK, 392);
        contentValues.put(PRODUCTS_SALES, 428);
        contentValues.put(PRODUCTS_MAGNITUDE, "46");
        contentValues.put(PRODUCTS_BRANDS, "Sketchers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 73);
        contentValues.put(PRODUCT_PIC, R.drawable.pic74); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slippers");
        contentValues.put(PRODUCTS_PRICE, 439);
        contentValues.put(PRODUCTS_STOCK, 732);
        contentValues.put(PRODUCTS_SALES, 282);
        contentValues.put(PRODUCTS_MAGNITUDE, "41");
        contentValues.put(PRODUCTS_BRANDS, "Sketchers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 74);
        contentValues.put(PRODUCT_PIC, R.drawable.pic75); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Boots");
        contentValues.put(PRODUCTS_PRICE, 1500);
        contentValues.put(PRODUCTS_STOCK, 362);
        contentValues.put(PRODUCTS_SALES, 153);
        contentValues.put(PRODUCTS_MAGNITUDE, "45");
        contentValues.put(PRODUCTS_BRANDS, "CAT");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 75);
        contentValues.put(PRODUCT_PIC, R.drawable.pic76); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 980);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 1129);
        contentValues.put(PRODUCTS_MAGNITUDE, "42");
        contentValues.put(PRODUCTS_BRANDS, "Sketchers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 76);
        contentValues.put(PRODUCT_PIC, R.drawable.pic77); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantoffels");
        contentValues.put(PRODUCTS_PRICE, 253);
        contentValues.put(PRODUCTS_STOCK, 587);
        contentValues.put(PRODUCTS_SALES, 869);
        contentValues.put(PRODUCTS_MAGNITUDE, "44");
        contentValues.put(PRODUCTS_BRANDS, "Comfortable Wear");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 77);
        contentValues.put(PRODUCT_PIC, R.drawable.pic78); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slippers");
        contentValues.put(PRODUCTS_PRICE, 708);
        contentValues.put(PRODUCTS_STOCK, 26);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "40");
        contentValues.put(PRODUCTS_BRANDS, "Adidas");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 78);
        contentValues.put(PRODUCT_PIC, R.drawable.pic79); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slippers");
        contentValues.put(PRODUCTS_PRICE, 697);
        contentValues.put(PRODUCTS_STOCK, 965);
        contentValues.put(PRODUCTS_SALES, 495);
        contentValues.put(PRODUCTS_MAGNITUDE, "43");
        contentValues.put(PRODUCTS_BRANDS, "Adidas");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 79);
        contentValues.put(PRODUCT_PIC, R.drawable.pic80); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slippers");
        contentValues.put(PRODUCTS_PRICE, 382);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "41");
        contentValues.put(PRODUCTS_BRANDS, "YoaHua");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

//        contentValues.put(PRODUCTS_ID, 80);
//        contentValues.put(PRODUCT_PIC, R.drawable.pic81); //Picture zette , maar t is op String
//        contentValues.put(PRODUCTS_NAME, "Pantoffels");
//        contentValues.put(PRODUCTS_PRICE, 251);
//        contentValues.put(PRODUCTS_STOCK, 837);
//        contentValues.put(PRODUCTS_SALES, 371);
//        contentValues.put(PRODUCTS_MAGNITUDE, "45");
//        contentValues.put(PRODUCTS_BRANDS, "Comfortable Wear");
//        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
//        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 81);
        contentValues.put(PRODUCT_PIC, R.drawable.pic82); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Loafers");
        contentValues.put(PRODUCTS_PRICE, 1496);
        contentValues.put(PRODUCTS_STOCK, 322);
        contentValues.put(PRODUCTS_SALES, 173);
        contentValues.put(PRODUCTS_MAGNITUDE, "45");
        contentValues.put(PRODUCTS_BRANDS, "Royals");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 82);
        contentValues.put(PRODUCT_PIC, R.drawable.pic83); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sandals");
        contentValues.put(PRODUCTS_PRICE, 697);
        contentValues.put(PRODUCTS_STOCK, 492);
        contentValues.put(PRODUCTS_SALES, 492);
        contentValues.put(PRODUCTS_MAGNITUDE, "43");
        contentValues.put(PRODUCTS_BRANDS, "CAT");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 83);
        contentValues.put(PRODUCT_PIC, R.drawable.pic84); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slippers");
        contentValues.put(PRODUCTS_PRICE, 1022);
        contentValues.put(PRODUCTS_STOCK, 472);
        contentValues.put(PRODUCTS_SALES, 492);
        contentValues.put(PRODUCTS_MAGNITUDE, "45");
        contentValues.put(PRODUCTS_BRANDS, "Birkenstock");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 84);
        contentValues.put(PRODUCT_PIC, R.drawable.pic85); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sandals");
        contentValues.put(PRODUCTS_PRICE, 450);
        contentValues.put(PRODUCTS_STOCK, 02);
        contentValues.put(PRODUCTS_SALES, 952);
        contentValues.put(PRODUCTS_MAGNITUDE, "42");
        contentValues.put(PRODUCTS_BRANDS, "Birkenstock");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 85);
        contentValues.put(PRODUCT_PIC, R.drawable.pic86); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Crocs");
        contentValues.put(PRODUCTS_PRICE, 291);
        contentValues.put(PRODUCTS_STOCK, 838);
        contentValues.put(PRODUCTS_SALES, 397);
        contentValues.put(PRODUCTS_MAGNITUDE, "45");
        contentValues.put(PRODUCTS_BRANDS, "Airwalk");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 86);
        contentValues.put(PRODUCT_PIC, R.drawable.pic87); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Crocs");
        contentValues.put(PRODUCTS_PRICE, 295);
        contentValues.put(PRODUCTS_STOCK, 292);
        contentValues.put(PRODUCTS_SALES, 239);
        contentValues.put(PRODUCTS_MAGNITUDE, "40");
        contentValues.put(PRODUCTS_BRANDS, "Veggies");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 87);
        contentValues.put(PRODUCT_PIC, R.drawable.pic88); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Crocs");
        contentValues.put(PRODUCTS_PRICE, 231);
        contentValues.put(PRODUCTS_STOCK, 472);
        contentValues.put(PRODUCTS_SALES, 832);
        contentValues.put(PRODUCTS_MAGNITUDE, "39");
        contentValues.put(PRODUCTS_BRANDS, "Crocs");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 88);
        contentValues.put(PRODUCT_PIC, R.drawable.pic89); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Loafers");
        contentValues.put(PRODUCTS_PRICE, 1390);
        contentValues.put(PRODUCTS_STOCK, 281);
        contentValues.put(PRODUCTS_SALES, 123);
        contentValues.put(PRODUCTS_MAGNITUDE, "45");
        contentValues.put(PRODUCTS_BRANDS, "Flite");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 89);
        contentValues.put(PRODUCT_PIC, R.drawable.pic90); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Loafers");
        contentValues.put(PRODUCTS_PRICE, 1700);
        contentValues.put(PRODUCTS_STOCK, 172);
        contentValues.put(PRODUCTS_SALES, 198);
        contentValues.put(PRODUCTS_MAGNITUDE, "41");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 90);
        contentValues.put(PRODUCT_PIC, R.drawable.pic91); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Loafers");
        contentValues.put(PRODUCTS_PRICE, 1250);
        contentValues.put(PRODUCTS_STOCK, 283);
        contentValues.put(PRODUCTS_SALES, 283);
        contentValues.put(PRODUCTS_MAGNITUDE, "45");
        contentValues.put(PRODUCTS_BRANDS, "Cole Haan");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 91);
        contentValues.put(PRODUCT_PIC, R.drawable.pic92); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Loafers");
        contentValues.put(PRODUCTS_PRICE, 2000);
        contentValues.put(PRODUCTS_STOCK, 292);
        contentValues.put(PRODUCTS_SALES, 193);
        contentValues.put(PRODUCTS_MAGNITUDE, "43");
        contentValues.put(PRODUCTS_BRANDS, "Louis Vuitton");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 92);
        contentValues.put(PRODUCT_PIC, R.drawable.pic93); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 592);
        contentValues.put(PRODUCTS_STOCK, 173);
        contentValues.put(PRODUCTS_SALES, 183);
        contentValues.put(PRODUCTS_MAGNITUDE, "40");
        contentValues.put(PRODUCTS_BRANDS, "Jack&Chris");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 93);
        contentValues.put(PRODUCT_PIC, R.drawable.pic94); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 639);
        contentValues.put(PRODUCTS_STOCK, 392);
        contentValues.put(PRODUCTS_SALES, 183);
        contentValues.put(PRODUCTS_MAGNITUDE, "37");
        contentValues.put(PRODUCTS_BRANDS, "Fendi");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 94);
        contentValues.put(PRODUCT_PIC, R.drawable.pic95); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 722);
        contentValues.put(PRODUCTS_STOCK, 123);
        contentValues.put(PRODUCTS_SALES, 492);
        contentValues.put(PRODUCTS_MAGNITUDE, "41");
        contentValues.put(PRODUCTS_BRANDS, "Coach");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 95);
        contentValues.put(PRODUCT_PIC, R.drawable.pic96); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 592);
        contentValues.put(PRODUCTS_STOCK, 293);
        contentValues.put(PRODUCTS_SALES, 103);
        contentValues.put(PRODUCTS_MAGNITUDE, "41");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 96);
        contentValues.put(PRODUCT_PIC, R.drawable.pic97); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 391);
        contentValues.put(PRODUCTS_STOCK, 293);
        contentValues.put(PRODUCTS_SALES, 183);
        contentValues.put(PRODUCTS_MAGNITUDE, "37");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 97);
        contentValues.put(PRODUCT_PIC, R.drawable.pic98); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 492);
        contentValues.put(PRODUCTS_STOCK, 292);
        contentValues.put(PRODUCTS_SALES, 492);
        contentValues.put(PRODUCTS_MAGNITUDE, "39");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 98);
        contentValues.put(PRODUCT_PIC, R.drawable.pic99); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 242);
        contentValues.put(PRODUCTS_STOCK, 193);
        contentValues.put(PRODUCTS_SALES, 294);
        contentValues.put(PRODUCTS_MAGNITUDE, "40");
        contentValues.put(PRODUCTS_BRANDS, "Vans");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 99);
        contentValues.put(PRODUCT_PIC, R.drawable.pic100); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 549);
        contentValues.put(PRODUCTS_STOCK, 493);
        contentValues.put(PRODUCTS_SALES, 193);
        contentValues.put(PRODUCTS_MAGNITUDE, "41");
        contentValues.put(PRODUCTS_BRANDS, "Everlane");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 100);
        contentValues.put(PRODUCT_PIC, R.drawable.pic101); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 798);
        contentValues.put(PRODUCTS_STOCK, 246);
        contentValues.put(PRODUCTS_SALES, 476);
        contentValues.put(PRODUCTS_MAGNITUDE, "45");
        contentValues.put(PRODUCTS_BRANDS, "Prada");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 101);
        contentValues.put(PRODUCT_PIC, R.drawable.pic102); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 394);
        contentValues.put(PRODUCTS_STOCK, 103);
        contentValues.put(PRODUCTS_SALES, 183);
        contentValues.put(PRODUCTS_MAGNITUDE, "43");
        contentValues.put(PRODUCTS_BRANDS, "Finer");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 102);
        contentValues.put(PRODUCT_PIC, R.drawable.pic103); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 1029);
        contentValues.put(PRODUCTS_STOCK, 294);
        contentValues.put(PRODUCTS_SALES, 294);
        contentValues.put(PRODUCTS_MAGNITUDE, "39");
        contentValues.put(PRODUCTS_BRANDS, "Louis Vuitton");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 103);
        contentValues.put(PRODUCT_PIC, R.drawable.pic104); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 999);
        contentValues.put(PRODUCTS_STOCK, 284);
        contentValues.put(PRODUCTS_SALES, 409);
        contentValues.put(PRODUCTS_MAGNITUDE, "41");
        contentValues.put(PRODUCTS_BRANDS, "Gucci");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 104);
        contentValues.put(PRODUCT_PIC, R.drawable.pic105); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 1299);
        contentValues.put(PRODUCTS_STOCK, 21);
        contentValues.put(PRODUCTS_SALES, 294);
        contentValues.put(PRODUCTS_MAGNITUDE, "43");
        contentValues.put(PRODUCTS_BRANDS, "Jordan");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 105);
        contentValues.put(PRODUCT_PIC, R.drawable.pic106); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 255);
        contentValues.put(PRODUCTS_STOCK, 108);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Calvin Klein");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);


        contentValues.put(PRODUCTS_ID, 106);
        contentValues.put(PRODUCT_PIC, R.drawable.pic107); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Short skirts");
        contentValues.put(PRODUCTS_PRICE, 255);
        contentValues.put(PRODUCTS_STOCK, 108);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "DressBerry");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);


        contentValues.put(PRODUCTS_ID, 107);
        contentValues.put(PRODUCT_PIC, R.drawable.pic108); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Short skirts");
        contentValues.put(PRODUCTS_PRICE, 195);
        contentValues.put(PRODUCTS_STOCK, 66);
        contentValues.put(PRODUCTS_SALES, 93);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Biba");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);


        contentValues.put(PRODUCTS_ID, 108);
        contentValues.put(PRODUCT_PIC, R.drawable.pic109); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Long skirts");
        contentValues.put(PRODUCTS_PRICE, 354);
        contentValues.put(PRODUCTS_STOCK, 220);
        contentValues.put(PRODUCTS_SALES, 178);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Forever 21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);


        contentValues.put(PRODUCTS_ID, 109);
        contentValues.put(PRODUCT_PIC, R.drawable.pic110); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Long skirt");
        contentValues.put(PRODUCTS_PRICE, 354);
        contentValues.put(PRODUCTS_STOCK, 166);
        contentValues.put(PRODUCTS_SALES, 297);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Vero Moda");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);


        contentValues.put(PRODUCTS_ID, 110);
        contentValues.put(PRODUCT_PIC, R.drawable.pic111); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Long skirts");
        contentValues.put(PRODUCTS_PRICE, 197);
        contentValues.put(PRODUCTS_STOCK, 209);
        contentValues.put(PRODUCTS_SALES, 210);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Alfani");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);


        contentValues.put(PRODUCTS_ID, 111);
        contentValues.put(PRODUCT_PIC, R.drawable.pic112); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "High Low skirts");
        contentValues.put(PRODUCTS_PRICE, 320);
        contentValues.put(PRODUCTS_STOCK, 288);
        contentValues.put(PRODUCTS_SALES, 221);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Calvin Klein");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);


        contentValues.put(PRODUCTS_ID, 112);
        contentValues.put(PRODUCT_PIC, R.drawable.pic113); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "High Low skirts");
        contentValues.put(PRODUCTS_PRICE, 312);
        contentValues.put(PRODUCTS_STOCK, 375);
        contentValues.put(PRODUCTS_SALES, 366);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Forever 21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);


        contentValues.put(PRODUCTS_ID, 113);
        contentValues.put(PRODUCT_PIC, R.drawable.pic114); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "High Low skirts");
        contentValues.put(PRODUCTS_PRICE, 390);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 199);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Nine West");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);


        contentValues.put(PRODUCTS_ID, 114);
        contentValues.put(PRODUCT_PIC, R.drawable.pic115); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "short skirts");
        contentValues.put(PRODUCTS_PRICE, 321);
        contentValues.put(PRODUCTS_STOCK, 185);
        contentValues.put(PRODUCTS_SALES, 173);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Biba");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);


        contentValues.put(PRODUCTS_ID, 115);
        contentValues.put(PRODUCT_PIC, R.drawable.pic116); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Long skirts");
        contentValues.put(PRODUCTS_PRICE, 165);
        contentValues.put(PRODUCTS_STOCK, 77);
        contentValues.put(PRODUCTS_SALES, 69);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Tahari ASL");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 116);
        contentValues.put(PRODUCT_PIC, R.drawable.pic117); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Long skirts");
        contentValues.put(PRODUCTS_PRICE, 472);
        contentValues.put(PRODUCTS_STOCK, 301);
        contentValues.put(PRODUCTS_SALES, 244);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Anne Klein");
        contentValues.put(PRODUCTS_CATEGORY_ID, 5);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 117);
        contentValues.put(PRODUCT_PIC, R.drawable.pic118); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sleeves Tops");
        contentValues.put(PRODUCTS_PRICE, 364);
        contentValues.put(PRODUCTS_STOCK, 308);
        contentValues.put(PRODUCTS_SALES, 287);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Forever 21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 118);
        contentValues.put(PRODUCT_PIC, R.drawable.pic119); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Off shoulder sleeves");
        contentValues.put(PRODUCTS_PRICE, 288);
        contentValues.put(PRODUCTS_STOCK, 407);
        contentValues.put(PRODUCTS_SALES, 355);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "EverLane");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 119);
        contentValues.put(PRODUCT_PIC, R.drawable.pic120); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Off shoulder");
        contentValues.put(PRODUCTS_PRICE, 289);
        contentValues.put(PRODUCTS_STOCK, 117);
        contentValues.put(PRODUCTS_SALES, 100);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Banana Republic");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 120);
        contentValues.put(PRODUCT_PIC, R.drawable.pic121); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Off shoulder sleeves");
        contentValues.put(PRODUCTS_PRICE, 274);
        contentValues.put(PRODUCTS_STOCK, 398);
        contentValues.put(PRODUCTS_SALES, 264);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Express");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 121);
        contentValues.put(PRODUCT_PIC, R.drawable.pic122); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sleeves top");
        contentValues.put(PRODUCTS_PRICE, 217);
        contentValues.put(PRODUCTS_STOCK, 320);
        contentValues.put(PRODUCTS_SALES, 275);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Forever 21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 122);
        contentValues.put(PRODUCT_PIC, R.drawable.pic123); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Off shoulder sleeves");
        contentValues.put(PRODUCTS_PRICE, 198);
        contentValues.put(PRODUCTS_STOCK, 226);
        contentValues.put(PRODUCTS_SALES, 209);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Banana Republic");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 123);
        contentValues.put(PRODUCT_PIC, R.drawable.pic124); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Blouse");
        contentValues.put(PRODUCTS_PRICE, 434);
        contentValues.put(PRODUCTS_STOCK, 155);
        contentValues.put(PRODUCTS_SALES, 76);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Forever 21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 124);
        contentValues.put(PRODUCT_PIC, R.drawable.pic125); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Short Dresses");
        contentValues.put(PRODUCTS_PRICE, 441);
        contentValues.put(PRODUCTS_STOCK, 323);
        contentValues.put(PRODUCTS_SALES, 301);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "H&M");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 125);
        contentValues.put(PRODUCT_PIC, R.drawable.pic126); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Long Dresses");
        contentValues.put(PRODUCTS_PRICE, 273);
        contentValues.put(PRODUCTS_STOCK, 122);
        contentValues.put(PRODUCTS_SALES, 97);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Rixo");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 126);
        contentValues.put(PRODUCT_PIC, R.drawable.pic127); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Short Dresses");
        contentValues.put(PRODUCTS_PRICE, 495);
        contentValues.put(PRODUCTS_STOCK, 223);
        contentValues.put(PRODUCTS_SALES, 198);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Forever 21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 127);
        contentValues.put(PRODUCT_PIC, R.drawable.pic128); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Long Dresses");
        contentValues.put(PRODUCTS_PRICE, 328);
        contentValues.put(PRODUCTS_STOCK, 287);
        contentValues.put(PRODUCTS_SALES, 199);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Forever 21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 128);
        contentValues.put(PRODUCT_PIC, R.drawable.pic129); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 86);
        contentValues.put(PRODUCTS_STOCK, 133);
        contentValues.put(PRODUCTS_SALES, 111);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Roxy");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 129);
        contentValues.put(PRODUCT_PIC, R.drawable.pic130); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 253);
        contentValues.put(PRODUCTS_STOCK, 238);
        contentValues.put(PRODUCTS_SALES, 199);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Levi's");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 130);
        contentValues.put(PRODUCT_PIC, R.drawable.pic131); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 154);
        contentValues.put(PRODUCTS_STOCK, 333);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "H&M");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 131);
        contentValues.put(PRODUCT_PIC, R.drawable.pic132); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 375);
        contentValues.put(PRODUCTS_STOCK, 77);
        contentValues.put(PRODUCTS_SALES, 66);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Levis's");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 132);
        contentValues.put(PRODUCT_PIC, R.drawable.pic133); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Sweatpants");
        contentValues.put(PRODUCTS_PRICE, 456);
        contentValues.put(PRODUCTS_STOCK, 198);
        contentValues.put(PRODUCTS_SALES, 155);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Banana Republic");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 133);
        contentValues.put(PRODUCT_PIC, R.drawable.pic134); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 431);
        contentValues.put(PRODUCTS_STOCK, 246);
        contentValues.put(PRODUCTS_SALES, 198);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Levi's");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 134);
        contentValues.put(PRODUCT_PIC, R.drawable.pic135); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 289);
        contentValues.put(PRODUCTS_STOCK, 198);
        contentValues.put(PRODUCTS_SALES, 88);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Tahari ASL");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 135);
        contentValues.put(PRODUCT_PIC, R.drawable.pic136); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 788);
        contentValues.put(PRODUCTS_STOCK, 566);
        contentValues.put(PRODUCTS_SALES, 465);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Levi's");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 136);
        contentValues.put(PRODUCT_PIC, R.drawable.pic137); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 654);
        contentValues.put(PRODUCTS_STOCK, 354);
        contentValues.put(PRODUCTS_SALES, 308);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Calvin Klein");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 137);
        contentValues.put(PRODUCT_PIC, R.drawable.pic138); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 354);
        contentValues.put(PRODUCTS_STOCK, 300);
        contentValues.put(PRODUCTS_SALES, 298);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Banana Republic");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 138);
        contentValues.put(PRODUCT_PIC, R.drawable.pic139); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jeans");
        contentValues.put(PRODUCTS_PRICE, 254);
        contentValues.put(PRODUCTS_STOCK, 202);
        contentValues.put(PRODUCTS_SALES, 157);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "H&M");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 139);
        contentValues.put(PRODUCT_PIC, R.drawable.pic140); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 890);
        contentValues.put(PRODUCTS_STOCK, 398);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Puma");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 140);
        contentValues.put(PRODUCT_PIC, R.drawable.pic141); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Heels");
        contentValues.put(PRODUCTS_PRICE, 576);
        contentValues.put(PRODUCTS_STOCK, 303);
        contentValues.put(PRODUCTS_SALES, 301);
        contentValues.put(PRODUCTS_MAGNITUDE, "37");
        contentValues.put(PRODUCTS_BRANDS, "Cherry MS");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 141);
        contentValues.put(PRODUCT_PIC, R.drawable.pic142); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Heels");
        contentValues.put(PRODUCTS_PRICE, 773);
        contentValues.put(PRODUCTS_STOCK, 13);
        contentValues.put(PRODUCTS_SALES, 284);
        contentValues.put(PRODUCTS_MAGNITUDE, "39");
        contentValues.put(PRODUCTS_BRANDS, "M. Gemi The Esatto");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 142);
        contentValues.put(PRODUCT_PIC, R.drawable.pic143); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Heels");
        contentValues.put(PRODUCTS_PRICE, 638);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 353);
        contentValues.put(PRODUCTS_MAGNITUDE, "40");
        contentValues.put(PRODUCTS_BRANDS, "Christian Louboutin Alminette");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 143);
        contentValues.put(PRODUCT_PIC, R.drawable.pic144); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 869);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "37");
        contentValues.put(PRODUCTS_BRANDS, "Balenciaga");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 144);
        contentValues.put(PRODUCT_PIC, R.drawable.pic145); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 999);
        contentValues.put(PRODUCTS_STOCK, 293);
        contentValues.put(PRODUCTS_SALES, 144);
        contentValues.put(PRODUCTS_MAGNITUDE, "41");
        contentValues.put(PRODUCTS_BRANDS, "Aramani Exchange");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 145);
        contentValues.put(PRODUCT_PIC, R.drawable.pic146); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 1200);
        contentValues.put(PRODUCTS_STOCK, 233);
        contentValues.put(PRODUCTS_SALES, 392);
        contentValues.put(PRODUCTS_MAGNITUDE, "39");
        contentValues.put(PRODUCTS_BRANDS, "Nike");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 146);
        contentValues.put(PRODUCT_PIC, R.drawable.pic147); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 1159);
        contentValues.put(PRODUCTS_STOCK, 283);
        contentValues.put(PRODUCTS_SALES, 427);
        contentValues.put(PRODUCTS_MAGNITUDE, "37");
        contentValues.put(PRODUCTS_BRANDS, "All Star");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 147);
        contentValues.put(PRODUCT_PIC, R.drawable.pic148); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 792);
        contentValues.put(PRODUCTS_STOCK, 181);
        contentValues.put(PRODUCTS_SALES, 272);
        contentValues.put(PRODUCTS_MAGNITUDE, "36");
        contentValues.put(PRODUCTS_BRANDS, "Sketchers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 148);
        contentValues.put(PRODUCT_PIC, R.drawable.pic149); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 491);
        contentValues.put(PRODUCTS_STOCK, 301);
        contentValues.put(PRODUCTS_SALES, 284);
        contentValues.put(PRODUCTS_MAGNITUDE, "40");
        contentValues.put(PRODUCTS_BRANDS, "Sketchers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 149);
        contentValues.put(PRODUCT_PIC, R.drawable.pic150); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 297);
        contentValues.put(PRODUCTS_STOCK, 12);
        contentValues.put(PRODUCTS_SALES, 238);
        contentValues.put(PRODUCTS_MAGNITUDE, "37");
        contentValues.put(PRODUCTS_BRANDS, "Sketchers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 150);
        contentValues.put(PRODUCT_PIC, R.drawable.pic151); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slippers");
        contentValues.put(PRODUCTS_PRICE, 592);
        contentValues.put(PRODUCTS_STOCK, 283);
        contentValues.put(PRODUCTS_SALES, 391);
        contentValues.put(PRODUCTS_MAGNITUDE, "39");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 151);
        contentValues.put(PRODUCT_PIC, R.drawable.pic152); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slippers");
        contentValues.put(PRODUCTS_PRICE, 592);
        contentValues.put(PRODUCTS_STOCK, 283);
        contentValues.put(PRODUCTS_SALES, 284);
        contentValues.put(PRODUCTS_MAGNITUDE, "39");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 152);
        contentValues.put(PRODUCT_PIC, R.drawable.pic153); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slippers");
        contentValues.put(PRODUCTS_PRICE, 273);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 493);
        contentValues.put(PRODUCTS_MAGNITUDE, "38");
        contentValues.put(PRODUCTS_BRANDS, "Moccasin");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 153);
        contentValues.put(PRODUCT_PIC, R.drawable.pic154); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pantoffels");
        contentValues.put(PRODUCTS_PRICE, 282);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 429);
        contentValues.put(PRODUCTS_MAGNITUDE, "40");
        contentValues.put(PRODUCTS_BRANDS, "UGG");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 154);
        contentValues.put(PRODUCT_PIC, R.drawable.pic155); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slippers");
        contentValues.put(PRODUCTS_PRICE, 482);
        contentValues.put(PRODUCTS_STOCK, 293);
        contentValues.put(PRODUCTS_SALES, 384);
        contentValues.put(PRODUCTS_MAGNITUDE, "39");
        contentValues.put(PRODUCTS_BRANDS, "BOBS");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 155);
        contentValues.put(PRODUCT_PIC, R.drawable.pic156); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 995);
        contentValues.put(PRODUCTS_STOCK, 428);
        contentValues.put(PRODUCTS_SALES, 582);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Cereal");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 156);
        contentValues.put(PRODUCT_PIC, R.drawable.pic157); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 1029);
        contentValues.put(PRODUCTS_STOCK, 283);
        contentValues.put(PRODUCTS_SALES, 931);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Gucci");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 157);
        contentValues.put(PRODUCT_PIC, R.drawable.pic158); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 995);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 192);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Aldo");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 158);
        contentValues.put(PRODUCT_PIC, R.drawable.pic159); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 1118);
        contentValues.put(PRODUCTS_STOCK, 481);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Loungefly");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 159);
        contentValues.put(PRODUCT_PIC, R.drawable.pic160); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 792);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 522);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Fossil");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 160);
        contentValues.put(PRODUCT_PIC, R.drawable.pic161); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 842);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 385);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Coach");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 161);
        contentValues.put(PRODUCT_PIC, R.drawable.pic162); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 835);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 481);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Fossil");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 162);
        contentValues.put(PRODUCT_PIC, R.drawable.pic163); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 672);
        contentValues.put(PRODUCTS_STOCK, 381);
        contentValues.put(PRODUCTS_SALES, 41);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Michael Kors");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 163);
        contentValues.put(PRODUCT_PIC, R.drawable.pic164); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 748);
        contentValues.put(PRODUCTS_STOCK, 582);
        contentValues.put(PRODUCTS_SALES, 822);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Loungefly");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 164);
        contentValues.put(PRODUCT_PIC, R.drawable.pic165); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 832);
        contentValues.put(PRODUCTS_STOCK, 428);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Aldo");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 165);
        contentValues.put(PRODUCT_PIC, R.drawable.pic166); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 361);
        contentValues.put(PRODUCTS_STOCK, 136);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 166);
        contentValues.put(PRODUCT_PIC, R.drawable.pic167); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 252);
        contentValues.put(PRODUCTS_STOCK, 462);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Forever21");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 167);
        contentValues.put(PRODUCT_PIC, R.drawable.pic168); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 483);
        contentValues.put(PRODUCTS_STOCK, 472);
        contentValues.put(PRODUCTS_SALES, 422);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Tassel");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 168);
        contentValues.put(PRODUCT_PIC, R.drawable.pic169); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Boxers");
        contentValues.put(PRODUCTS_PRICE, 198);
        contentValues.put(PRODUCTS_STOCK, 500);
        contentValues.put(PRODUCTS_SALES, 379);
        contentValues.put(PRODUCTS_MAGNITUDE, "27");
        contentValues.put(PRODUCTS_BRANDS, "Hugo boss");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 169);
        contentValues.put(PRODUCT_PIC, R.drawable.pic170); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Boxers");
        contentValues.put(PRODUCTS_PRICE, 398);
        contentValues.put(PRODUCTS_STOCK, 354);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "27");
        contentValues.put(PRODUCTS_BRANDS, "Chill Boys");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 170);
        contentValues.put(PRODUCT_PIC, R.drawable.pic171); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Brief Boxers");
        contentValues.put(PRODUCTS_PRICE, 252);
        contentValues.put(PRODUCTS_STOCK, 432);
        contentValues.put(PRODUCTS_SALES, 351);
        contentValues.put(PRODUCTS_MAGNITUDE, "25");
        contentValues.put(PRODUCTS_BRANDS, "MEN");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 171);
        contentValues.put(PRODUCT_PIC, R.drawable.pic172); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Brief Boxers");
        contentValues.put(PRODUCTS_PRICE, 479);
        contentValues.put(PRODUCTS_STOCK, 298);
        contentValues.put(PRODUCTS_SALES, 188);
        contentValues.put(PRODUCTS_MAGNITUDE, "28");
        contentValues.put(PRODUCTS_BRANDS, "Jack&Jones");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 172);
        contentValues.put(PRODUCT_PIC, R.drawable.pic173); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Brief Boxers");
        contentValues.put(PRODUCTS_PRICE, 144);
        contentValues.put(PRODUCTS_STOCK, 499);
        contentValues.put(PRODUCTS_SALES, 398);
        contentValues.put(PRODUCTS_MAGNITUDE, "30");
        contentValues.put(PRODUCTS_BRANDS, "MEN");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 173);
        contentValues.put(PRODUCT_PIC, R.drawable.pic174); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Brief Boxers");
        contentValues.put(PRODUCTS_PRICE, 365);
        contentValues.put(PRODUCTS_STOCK, 322);
        contentValues.put(PRODUCTS_SALES, 122);
        contentValues.put(PRODUCTS_MAGNITUDE, "29");
        contentValues.put(PRODUCTS_BRANDS, "BENCH/BODY");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 174);
        contentValues.put(PRODUCT_PIC, R.drawable.pic175); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Boxers");
        contentValues.put(PRODUCTS_PRICE, 585);
        contentValues.put(PRODUCTS_STOCK, 101);
        contentValues.put(PRODUCTS_SALES, 77);
        contentValues.put(PRODUCTS_MAGNITUDE, "28");
        contentValues.put(PRODUCTS_BRANDS, "Calvin Klein");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 175);
        contentValues.put(PRODUCT_PIC, R.drawable.pic176); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slings");
        contentValues.put(PRODUCTS_PRICE, 444);
        contentValues.put(PRODUCTS_STOCK, 100);
        contentValues.put(PRODUCTS_SALES, 44);
        contentValues.put(PRODUCTS_MAGNITUDE, "24");
        contentValues.put(PRODUCTS_BRANDS, "Calvin Klein");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 176);
        contentValues.put(PRODUCT_PIC, R.drawable.pic177); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Brief Boxers");
        contentValues.put(PRODUCTS_PRICE, 404);
        contentValues.put(PRODUCTS_STOCK, 311);
        contentValues.put(PRODUCTS_SALES, 110);
        contentValues.put(PRODUCTS_MAGNITUDE, "25");
        contentValues.put(PRODUCTS_BRANDS, "Calvin Klein");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 177);
        contentValues.put(PRODUCT_PIC, R.drawable.pic178); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slings");
        contentValues.put(PRODUCTS_PRICE, 192);
        contentValues.put(PRODUCTS_STOCK, 199);
        contentValues.put(PRODUCTS_SALES, 101);
        contentValues.put(PRODUCTS_MAGNITUDE, "27");
        contentValues.put(PRODUCTS_BRANDS, "Package");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 178);
        contentValues.put(PRODUCT_PIC, R.drawable.pic179); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume Sauvage");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Dior");
        contentValues.put(PRODUCTS_CATEGORY_ID, 12);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 179);
        contentValues.put(PRODUCT_PIC, R.drawable.pic180); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume Men in Black");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "BVLGARI");
        contentValues.put(PRODUCTS_CATEGORY_ID, 12);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 180);
        contentValues.put(PRODUCT_PIC, R.drawable.pic181); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume gucci");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "GUCCI");
        contentValues.put(PRODUCTS_CATEGORY_ID, 12);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 181);
        contentValues.put(PRODUCT_PIC, R.drawable.pic182); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume Bleu de Chanel");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "CHANEL");
        contentValues.put(PRODUCTS_CATEGORY_ID, 12);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 182);
        contentValues.put(PRODUCT_PIC, R.drawable.pic183); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume 1 million");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "1 million");
        contentValues.put(PRODUCTS_CATEGORY_ID, 12);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 183);
        contentValues.put(PRODUCT_PIC, R.drawable.pic184); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume versace");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "VERSACE");
        contentValues.put(PRODUCTS_CATEGORY_ID, 12);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 184);
        contentValues.put(PRODUCT_PIC, R.drawable.pic185); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume hugo boss");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "HUGO BOSS");
        contentValues.put(PRODUCTS_CATEGORY_ID, 12);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 185);
        contentValues.put(PRODUCT_PIC, R.drawable.pic186); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume Davidoff cool water");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Cool Water");
        contentValues.put(PRODUCTS_CATEGORY_ID, 12);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 186);
        contentValues.put(PRODUCT_PIC, R.drawable.pic187); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Allure");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "CHANEL");
        contentValues.put(PRODUCTS_CATEGORY_ID, 12);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 187);
        contentValues.put(PRODUCT_PIC, R.drawable.pic188); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume JAGUAR");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "JAGUAR");
        contentValues.put(PRODUCTS_CATEGORY_ID, 12);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 188);
        contentValues.put(PRODUCT_PIC, R.drawable.pic189); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume");
        contentValues.put(PRODUCTS_PRICE, 679);
        contentValues.put(PRODUCTS_STOCK, 222);
        contentValues.put(PRODUCTS_SALES, 124);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "COCO CHANEL");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 189);
        contentValues.put(PRODUCT_PIC, R.drawable.pic190); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume");
        contentValues.put(PRODUCTS_PRICE, 1000);
        contentValues.put(PRODUCTS_STOCK, 476);
        contentValues.put(PRODUCTS_SALES, 287);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "GIORGIO ARMANI SI");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 190);
        contentValues.put(PRODUCT_PIC, R.drawable.pic191); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume");
        contentValues.put(PRODUCTS_PRICE, 1500);
        contentValues.put(PRODUCTS_STOCK, 550);
        contentValues.put(PRODUCTS_SALES, 444);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Lady Million");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 191);
        contentValues.put(PRODUCT_PIC, R.drawable.pic192); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume");
        contentValues.put(PRODUCTS_PRICE, 555);
        contentValues.put(PRODUCTS_STOCK, 333);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "D&G");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 192);
        contentValues.put(PRODUCT_PIC, R.drawable.pic193); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume");
        contentValues.put(PRODUCTS_PRICE, 777);
        contentValues.put(PRODUCTS_STOCK, 345);
        contentValues.put(PRODUCTS_SALES, 237);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "GUCCI");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 193);
        contentValues.put(PRODUCT_PIC, R.drawable.pic194); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume");
        contentValues.put(PRODUCTS_PRICE, 665);
        contentValues.put(PRODUCTS_STOCK, 353);
        contentValues.put(PRODUCTS_SALES, 188);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 194);
        contentValues.put(PRODUCT_PIC, R.drawable.pic195); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume Wild Orchids");
        contentValues.put(PRODUCTS_PRICE, 1800);
        contentValues.put(PRODUCTS_STOCK, 30);
        contentValues.put(PRODUCTS_SALES, 22);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Beyonce");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 195);
        contentValues.put(PRODUCT_PIC, R.drawable.pic196); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume");
        contentValues.put(PRODUCTS_PRICE, 443);
        contentValues.put(PRODUCTS_STOCK, 467);
        contentValues.put(PRODUCTS_SALES, 492);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Flower of Story");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 196);
        contentValues.put(PRODUCT_PIC, R.drawable.pic197); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume Irresistible");
        contentValues.put(PRODUCTS_PRICE, 783);
        contentValues.put(PRODUCTS_STOCK, 43);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Givenchy");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 197);
        contentValues.put(PRODUCT_PIC, R.drawable.pic198); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume Body Mist Secret Charm");
        contentValues.put(PRODUCTS_PRICE, 263);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Victoria's Secret");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 198);
        contentValues.put(PRODUCT_PIC, R.drawable.pic199); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume Body Mist Pure Seduction");
        contentValues.put(PRODUCTS_PRICE, 352);
        contentValues.put(PRODUCTS_STOCK, 92);
        contentValues.put(PRODUCTS_SALES, 271);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Victoria's Secret");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 199);
        contentValues.put(PRODUCT_PIC, R.drawable.pic200); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Perfume Body Mist Shot of Coconut");
        contentValues.put(PRODUCTS_PRICE, 252);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Victoria's Secret");
        contentValues.put(PRODUCTS_CATEGORY_ID, 11);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 200);
        contentValues.put(PRODUCT_PIC, R.drawable.pic201); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earring");
        contentValues.put(PRODUCTS_PRICE, 836);
        contentValues.put(PRODUCTS_STOCK, 383);
        contentValues.put(PRODUCTS_SALES, 232);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Luxurman");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 201);
        contentValues.put(PRODUCT_PIC, R.drawable.pic202); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earring");
        contentValues.put(PRODUCTS_PRICE, 632);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Swarovski");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 202);
        contentValues.put(PRODUCT_PIC, R.drawable.pic203); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earring");
        contentValues.put(PRODUCTS_PRICE, 522);
        contentValues.put(PRODUCTS_STOCK, 352);
        contentValues.put(PRODUCTS_SALES, 362);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Cartier");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 203);
        contentValues.put(PRODUCT_PIC, R.drawable.pic204); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earring");
        contentValues.put(PRODUCTS_PRICE, 243);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 462);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "David Yurman");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 204);
        contentValues.put(PRODUCT_PIC, R.drawable.pic205); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earring");
        contentValues.put(PRODUCTS_PRICE, 262);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 272);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Mikimoto");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 205);
        contentValues.put(PRODUCT_PIC, R.drawable.pic206); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earring");
        contentValues.put(PRODUCTS_PRICE, 532);
        contentValues.put(PRODUCTS_STOCK, 271);
        contentValues.put(PRODUCTS_SALES, 37);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Harry Winston");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 206);
        contentValues.put(PRODUCT_PIC, R.drawable.pic207); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earring");
        contentValues.put(PRODUCTS_PRICE, 362);
        contentValues.put(PRODUCTS_STOCK, 362);
        contentValues.put(PRODUCTS_SALES, 483);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Luxurman");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 207);
        contentValues.put(PRODUCT_PIC, R.drawable.pic208); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earring");
        contentValues.put(PRODUCTS_PRICE, 764);
        contentValues.put(PRODUCTS_STOCK, 362);
        contentValues.put(PRODUCTS_SALES, 248);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Bvlgari");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 208);
        contentValues.put(PRODUCT_PIC, R.drawable.pic209); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earring");
        contentValues.put(PRODUCTS_PRICE, 372);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Cartier");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 209);
        contentValues.put(PRODUCT_PIC, R.drawable.pic210); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 844);
        contentValues.put(PRODUCTS_STOCK, 391);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Cartier");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 210);
        contentValues.put(PRODUCT_PIC, R.drawable.pic211); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 843);
        contentValues.put(PRODUCTS_STOCK, 957);
        contentValues.put(PRODUCTS_SALES, 932);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Charm");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 211);
        contentValues.put(PRODUCT_PIC, R.drawable.pic212); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 625);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 592);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Luxurman");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 212);
        contentValues.put(PRODUCT_PIC, R.drawable.pic213); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 261);
        contentValues.put(PRODUCTS_STOCK, 842);
        contentValues.put(PRODUCTS_SALES, 381);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Victoriavine");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 213);
        contentValues.put(PRODUCT_PIC, R.drawable.pic214); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 522);
        contentValues.put(PRODUCTS_STOCK, 631);
        contentValues.put(PRODUCTS_SALES, 24);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Hermes Bracelet");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 214);
        contentValues.put(PRODUCT_PIC, R.drawable.pic215); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 632);
        contentValues.put(PRODUCTS_STOCK, 23);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Louis Vuitton");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 215);
        contentValues.put(PRODUCT_PIC, R.drawable.pic216); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Necklace");
        contentValues.put(PRODUCTS_PRICE, 585);
        contentValues.put(PRODUCTS_STOCK, 483);
        contentValues.put(PRODUCTS_SALES, 493);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Grace Lee");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 216);
        contentValues.put(PRODUCT_PIC, R.drawable.pic217); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Necklace");
        contentValues.put(PRODUCTS_PRICE, 824);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 593);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Loren Steward");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 217);
        contentValues.put(PRODUCT_PIC, R.drawable.pic218); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Necklace");
        contentValues.put(PRODUCTS_PRICE, 852);
        contentValues.put(PRODUCTS_STOCK, 13);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Monica Vinader");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 218);
        contentValues.put(PRODUCT_PIC, R.drawable.pic219); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Necklace");
        contentValues.put(PRODUCTS_PRICE, 624);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 134);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Swarovski");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 219);
        contentValues.put(PRODUCT_PIC, R.drawable.pic220); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Necklace");
        contentValues.put(PRODUCTS_PRICE, 964);
        contentValues.put(PRODUCTS_STOCK, 472);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 220);
        contentValues.put(PRODUCT_PIC, R.drawable.pic221); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Anklet");
        contentValues.put(PRODUCTS_PRICE, 241);
        contentValues.put(PRODUCTS_STOCK, 326);
        contentValues.put(PRODUCTS_SALES, 492);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Cartier");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 221);
        contentValues.put(PRODUCT_PIC, R.drawable.pic222); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Anklet");
        contentValues.put(PRODUCTS_PRICE, 432);
        contentValues.put(PRODUCTS_STOCK, 492);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "John Hardy");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 222);
        contentValues.put(PRODUCT_PIC, R.drawable.pic223); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Necklace");
        contentValues.put(PRODUCTS_PRICE, 253);
        contentValues.put(PRODUCTS_STOCK, 282);
        contentValues.put(PRODUCTS_SALES, 582);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Thomas Sabo");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 223);
        contentValues.put(PRODUCT_PIC, R.drawable.pic224); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Necklace");
        contentValues.put(PRODUCTS_PRICE, 482);
        contentValues.put(PRODUCTS_STOCK, 472);
        contentValues.put(PRODUCTS_SALES, 258);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Miansai");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 224);
        contentValues.put(PRODUCT_PIC, R.drawable.pic225); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Necklace");
        contentValues.put(PRODUCTS_PRICE, 894);
        contentValues.put(PRODUCTS_STOCK, 327);
        contentValues.put(PRODUCTS_SALES, 523);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Hugo Boss");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 225);
        contentValues.put(PRODUCT_PIC, R.drawable.pic226); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Necklace");
        contentValues.put(PRODUCTS_PRICE, 462);
        contentValues.put(PRODUCTS_STOCK, 572);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Le Gramme");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 226);
        contentValues.put(PRODUCT_PIC, R.drawable.pic227); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earrings");
        contentValues.put(PRODUCTS_PRICE, 361);
        contentValues.put(PRODUCTS_STOCK, 381);
        contentValues.put(PRODUCTS_SALES, 383);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "AMBUSH");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 227);
        contentValues.put(PRODUCT_PIC, R.drawable.pic228); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earrings");
        contentValues.put(PRODUCTS_PRICE, 251);
        contentValues.put(PRODUCTS_STOCK, 361);
        contentValues.put(PRODUCTS_SALES, 351);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "AMI Paris");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 228);
        contentValues.put(PRODUCT_PIC, R.drawable.pic229); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Earrings");
        contentValues.put(PRODUCTS_PRICE, 246);
        contentValues.put(PRODUCTS_STOCK, 731);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Alan Crocetti");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 229);
        contentValues.put(PRODUCT_PIC, R.drawable.pic230); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 573);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 246);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Orson Pull");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 230);
        contentValues.put(PRODUCT_PIC, R.drawable.pic231); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 793);
        contentValues.put(PRODUCTS_STOCK, 347);
        contentValues.put(PRODUCTS_SALES, 272);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Darwin");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 231);
        contentValues.put(PRODUCT_PIC, R.drawable.pic232); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 531);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 492);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Jaycy");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 232);
        contentValues.put(PRODUCT_PIC, R.drawable.pic233); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 252);
        contentValues.put(PRODUCTS_STOCK, 347);
        contentValues.put(PRODUCTS_SALES, 437);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "KD");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 233);
        contentValues.put(PRODUCT_PIC, R.drawable.pic234); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 50);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 423);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 234);
        contentValues.put(PRODUCT_PIC, R.drawable.pic235); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 847);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 282);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Royals");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 235);
        contentValues.put(PRODUCT_PIC, R.drawable.pic236); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 972);
        contentValues.put(PRODUCTS_STOCK, 263);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 236);
        contentValues.put(PRODUCT_PIC, R.drawable.pic237); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Bracelet");
        contentValues.put(PRODUCTS_PRICE, 926);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 32);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Hugo Boss");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 237);
        contentValues.put(PRODUCT_PIC, R.drawable.pic238); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 746);
        contentValues.put(PRODUCTS_STOCK, 371);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "BiDen");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 238);
        contentValues.put(PRODUCT_PIC, R.drawable.pic239); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 432);
        contentValues.put(PRODUCTS_STOCK, 361);
        contentValues.put(PRODUCTS_SALES, 371);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Curren");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 239);
        contentValues.put(PRODUCT_PIC, R.drawable.pic240); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 243);
        contentValues.put(PRODUCTS_STOCK, 478);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Olevs");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 240);
        contentValues.put(PRODUCT_PIC, R.drawable.pic241); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 742);
        contentValues.put(PRODUCTS_STOCK, 472);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Hermes");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 241);
        contentValues.put(PRODUCT_PIC, R.drawable.pic242); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 1700);
        contentValues.put(PRODUCTS_STOCK, 381);
        contentValues.put(PRODUCTS_SALES, 381);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Rolex");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 242);
        contentValues.put(PRODUCT_PIC, R.drawable.pic243); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 577);
        contentValues.put(PRODUCTS_STOCK, 312);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Casio");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 243);
        contentValues.put(PRODUCT_PIC, R.drawable.pic244); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 577);
        contentValues.put(PRODUCTS_STOCK, 639);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Casio");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 244);
        contentValues.put(PRODUCT_PIC, R.drawable.pic245); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 1299);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 245);
        contentValues.put(PRODUCT_PIC, R.drawable.pic246); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 1359);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 423);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 246);
        contentValues.put(PRODUCT_PIC, R.drawable.pic247); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 1399);
        contentValues.put(PRODUCTS_STOCK, 381);
        contentValues.put(PRODUCTS_SALES, 492);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Louis Vuitton");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 247);
        contentValues.put(PRODUCT_PIC, R.drawable.pic248); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 1009);
        contentValues.put(PRODUCTS_STOCK, 472);
        contentValues.put(PRODUCTS_SALES, 423);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Civo");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 248);
        contentValues.put(PRODUCT_PIC, R.drawable.pic249); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 959);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Casio");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 249);
        contentValues.put(PRODUCT_PIC, R.drawable.pic250); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Jewelry Watches");
        contentValues.put(PRODUCTS_PRICE, 1299);
        contentValues.put(PRODUCTS_STOCK, 472);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Michael Kors");
        contentValues.put(PRODUCTS_CATEGORY_ID, 13);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 250);
        contentValues.put(PRODUCT_PIC, R.drawable.pic251); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 276);
        contentValues.put(PRODUCTS_STOCK, 118);
        contentValues.put(PRODUCTS_SALES, 99);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Brixton Joanna");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 251);
        contentValues.put(PRODUCT_PIC, R.drawable.pic252); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 198);
        contentValues.put(PRODUCTS_STOCK, 50);
        contentValues.put(PRODUCTS_SALES, 22);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Nordstrom");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 252);
        contentValues.put(PRODUCT_PIC, R.drawable.pic253); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 243);
        contentValues.put(PRODUCTS_STOCK, 298);
        contentValues.put(PRODUCTS_SALES, 177);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "rag & bone");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 253);
        contentValues.put(PRODUCT_PIC, R.drawable.pic254); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 476);
        contentValues.put(PRODUCTS_STOCK, 298);
        contentValues.put(PRODUCTS_SALES, 187);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Under-Armour");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 254);
        contentValues.put(PRODUCT_PIC, R.drawable.pic255); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 555);
        contentValues.put(PRODUCTS_STOCK, 300);
        contentValues.put(PRODUCTS_SALES, 189);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Nike");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 255);
        contentValues.put(PRODUCT_PIC, R.drawable.pic256); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 59);
        contentValues.put(PRODUCTS_STOCK, 118);
        contentValues.put(PRODUCTS_SALES, 99);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "New Choice");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 256);
        contentValues.put(PRODUCT_PIC, R.drawable.pic257); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 444);
        contentValues.put(PRODUCTS_STOCK, 176);
        contentValues.put(PRODUCTS_SALES, 88);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Ralph Lauren");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 257);
        contentValues.put(PRODUCT_PIC, R.drawable.pic258); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 396);
        contentValues.put(PRODUCTS_STOCK, 201);
        contentValues.put(PRODUCTS_SALES, 184);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Ralph Lauren");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 258);
        contentValues.put(PRODUCT_PIC, R.drawable.pic259); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 432);
        contentValues.put(PRODUCTS_STOCK, 297);
        contentValues.put(PRODUCTS_SALES, 99);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Ralph Lauren");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 259);
        contentValues.put(PRODUCT_PIC, R.drawable.pic260); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 89);
        contentValues.put(PRODUCTS_STOCK, 88);
        contentValues.put(PRODUCTS_SALES, 54);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Brixton Joanna");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 260);
        contentValues.put(PRODUCT_PIC, R.drawable.pic261); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 120);
        contentValues.put(PRODUCTS_STOCK, 54);
        contentValues.put(PRODUCTS_SALES, 22);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Ganni");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 261);
        contentValues.put(PRODUCT_PIC, R.drawable.pic262); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 230);
        contentValues.put(PRODUCTS_STOCK, 22);
        contentValues.put(PRODUCTS_SALES, 11);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "kate spade new york");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 262);
        contentValues.put(PRODUCT_PIC, R.drawable.pic263); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 187);
        contentValues.put(PRODUCTS_STOCK, 88);
        contentValues.put(PRODUCTS_SALES, 50);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "kate spade new york");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 263);
        contentValues.put(PRODUCT_PIC, R.drawable.pic264); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 198);
        contentValues.put(PRODUCTS_STOCK, 60);
        contentValues.put(PRODUCTS_SALES, 34);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Brixton Joanna");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 264);
        contentValues.put(PRODUCT_PIC, R.drawable.pic265); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 301);
        contentValues.put(PRODUCTS_STOCK, 165);
        contentValues.put(PRODUCTS_SALES, 101);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Ganni");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 265);
        contentValues.put(PRODUCT_PIC, R.drawable.pic266); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 476);
        contentValues.put(PRODUCTS_STOCK, 290);
        contentValues.put(PRODUCTS_SALES, 187);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Nike");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 266);
        contentValues.put(PRODUCT_PIC, R.drawable.pic267); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 578);
        contentValues.put(PRODUCTS_STOCK, 354);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Nike");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 267);
        contentValues.put(PRODUCT_PIC, R.drawable.pic268); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 365);
        contentValues.put(PRODUCTS_STOCK, 109);
        contentValues.put(PRODUCTS_SALES, 100);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Nike");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 268);
        contentValues.put(PRODUCT_PIC, R.drawable.pic269); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 454);
        contentValues.put(PRODUCTS_STOCK, 298);
        contentValues.put(PRODUCTS_SALES, 101);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Nike");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 269);
        contentValues.put(PRODUCT_PIC, R.drawable.pic270); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 344);
        contentValues.put(PRODUCTS_STOCK, 555);
        contentValues.put(PRODUCTS_SALES, 332);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Mercedes");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 270);
        contentValues.put(PRODUCT_PIC, R.drawable.pic271); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 198);
        contentValues.put(PRODUCTS_STOCK, 300);
        contentValues.put(PRODUCTS_SALES, 287);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Chantelle");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 271);
        contentValues.put(PRODUCT_PIC, R.drawable.pic272); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 309);
        contentValues.put(PRODUCTS_STOCK, 456);
        contentValues.put(PRODUCTS_SALES, 398);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "La Perla");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 272);
        contentValues.put(PRODUCT_PIC, R.drawable.pic273); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 444);
        contentValues.put(PRODUCTS_STOCK, 333);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "DKNY");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 273);
        contentValues.put(PRODUCT_PIC, R.drawable.pic274); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 276);
        contentValues.put(PRODUCTS_STOCK, 118);
        contentValues.put(PRODUCTS_SALES, 99);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "lennel");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 274);
        contentValues.put(PRODUCT_PIC, R.drawable.pic275); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 388);
        contentValues.put(PRODUCTS_STOCK, 699);
        contentValues.put(PRODUCTS_SALES, 446);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "lennel");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 275);
        contentValues.put(PRODUCT_PIC, R.drawable.pic276); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 339);
        contentValues.put(PRODUCTS_STOCK, 389);
        contentValues.put(PRODUCTS_SALES, 307);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Aerie");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 276);
        contentValues.put(PRODUCT_PIC, R.drawable.pic277); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 456);
        contentValues.put(PRODUCTS_STOCK, 567);
        contentValues.put(PRODUCTS_SALES, 476);
        contentValues.put(PRODUCTS_MAGNITUDE, "XS");
        contentValues.put(PRODUCTS_BRANDS, "joek-jil");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 277);
        contentValues.put(PRODUCT_PIC, R.drawable.pic278); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 434);
        contentValues.put(PRODUCTS_STOCK, 677);
        contentValues.put(PRODUCTS_SALES, 467);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Under Armour");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 278);
        contentValues.put(PRODUCT_PIC, R.drawable.pic279); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 565);
        contentValues.put(PRODUCTS_STOCK, 656);
        contentValues.put(PRODUCTS_SALES, 543);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Victoria Secret");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 279);
        contentValues.put(PRODUCT_PIC, R.drawable.pic280); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 354);
        contentValues.put(PRODUCTS_STOCK, 432);
        contentValues.put(PRODUCTS_SALES, 323);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Chantelle");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 280);
        contentValues.put(PRODUCT_PIC, R.drawable.pic281); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Undergarments");
        contentValues.put(PRODUCTS_PRICE, 265);
        contentValues.put(PRODUCTS_STOCK, 354);
        contentValues.put(PRODUCTS_SALES, 254);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Calvin Klein");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 281);
        contentValues.put(PRODUCT_PIC, R.drawable.pic282); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Brow Pencil");
        contentValues.put(PRODUCTS_PRICE, 387);
        contentValues.put(PRODUCTS_STOCK, 302);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Anastasia");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 282);
        contentValues.put(PRODUCT_PIC, R.drawable.pic283); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Brow Pen");
        contentValues.put(PRODUCTS_PRICE, 287);
        contentValues.put(PRODUCTS_STOCK, 199);
        contentValues.put(PRODUCTS_SALES, 102);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "essence");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 283);
        contentValues.put(PRODUCT_PIC, R.drawable.pic284); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Mascara");
        contentValues.put(PRODUCTS_PRICE, 309);
        contentValues.put(PRODUCTS_STOCK, 287);
        contentValues.put(PRODUCTS_SALES, 177);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Maybeline");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 284);
        contentValues.put(PRODUCT_PIC, R.drawable.pic285); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Mascara");
        contentValues.put(PRODUCTS_PRICE, 285);
        contentValues.put(PRODUCTS_STOCK, 298);
        contentValues.put(PRODUCTS_SALES, 298);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Kush");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 285);
        contentValues.put(PRODUCT_PIC, R.drawable.pic286); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Eye Shadow Pallete");
        contentValues.put(PRODUCTS_PRICE, 599);
        contentValues.put(PRODUCTS_STOCK, 354);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Huda Beauty");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 286);
        contentValues.put(PRODUCT_PIC, R.drawable.pic287); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Eye Shadow Pallete");
        contentValues.put(PRODUCTS_PRICE, 567);
        contentValues.put(PRODUCTS_STOCK, 390);
        contentValues.put(PRODUCTS_SALES, 345);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Jefree Star");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 287);
        contentValues.put(PRODUCT_PIC, R.drawable.pic288); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Eye Liner");
        contentValues.put(PRODUCTS_PRICE, 387);
        contentValues.put(PRODUCTS_STOCK, 487);
        contentValues.put(PRODUCTS_SALES, 376);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "NYX");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 288);
        contentValues.put(PRODUCT_PIC, R.drawable.pic289); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Eye Liner");
        contentValues.put(PRODUCTS_PRICE, 390);
        contentValues.put(PRODUCTS_STOCK, 345);
        contentValues.put(PRODUCTS_SALES, 287);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "NYX");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 289);
        contentValues.put(PRODUCT_PIC, R.drawable.pic290); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Lipstick");
        contentValues.put(PRODUCTS_PRICE, 398);
        contentValues.put(PRODUCTS_STOCK, 278);
        contentValues.put(PRODUCTS_SALES, 166);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "MAC");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 290);
        contentValues.put(PRODUCT_PIC, R.drawable.pic291); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Lipstick");
        contentValues.put(PRODUCTS_PRICE, 487);
        contentValues.put(PRODUCTS_STOCK, 400);
        contentValues.put(PRODUCTS_SALES, 289);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Nykaa");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 291);
        contentValues.put(PRODUCT_PIC, R.drawable.pic292); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Lipstick");
        contentValues.put(PRODUCTS_PRICE, 590);
        contentValues.put(PRODUCTS_STOCK, 399);
        contentValues.put(PRODUCTS_SALES, 199);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Smashbox");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 292);
        contentValues.put(PRODUCT_PIC, R.drawable.pic293); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Lipstick");
        contentValues.put(PRODUCTS_PRICE, 589);
        contentValues.put(PRODUCTS_STOCK, 389);
        contentValues.put(PRODUCTS_SALES, 221);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "L'Oreal");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 293);
        contentValues.put(PRODUCT_PIC, R.drawable.pic294); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Contour Pallete");
        contentValues.put(PRODUCTS_PRICE, 678);
        contentValues.put(PRODUCTS_STOCK, 108);
        contentValues.put(PRODUCTS_SALES, 101);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Mac");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 294);
        contentValues.put(PRODUCT_PIC, R.drawable.pic295); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Contour Pallete");
        contentValues.put(PRODUCTS_PRICE, 456);
        contentValues.put(PRODUCTS_STOCK, 354);
        contentValues.put(PRODUCTS_SALES, 292);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Smashbox");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 295);
        contentValues.put(PRODUCT_PIC, R.drawable.pic296); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Make Up Contour Pallete");
        contentValues.put(PRODUCTS_PRICE, 365);
        contentValues.put(PRODUCTS_STOCK, 399);
        contentValues.put(PRODUCTS_SALES, 201);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Elf");
        contentValues.put(PRODUCTS_CATEGORY_ID, 18);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 296);
        contentValues.put(PRODUCT_PIC, R.drawable.pic297); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 382);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 492);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jack Rogers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 297);
        contentValues.put(PRODUCT_PIC, R.drawable.pic298); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 342);
        contentValues.put(PRODUCTS_STOCK, 381);
        contentValues.put(PRODUCTS_SALES, 371);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Jellycat");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 298);
        contentValues.put(PRODUCT_PIC, R.drawable.pic299); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 241);
        contentValues.put(PRODUCTS_STOCK, 422);
        contentValues.put(PRODUCTS_SALES, 271);
        contentValues.put(PRODUCTS_MAGNITUDE, "XS");
        contentValues.put(PRODUCTS_BRANDS, "Daily Paper");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 299);
        contentValues.put(PRODUCT_PIC, R.drawable.pic300); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 122);
        contentValues.put(PRODUCTS_STOCK, 26);
        contentValues.put(PRODUCTS_SALES, 361);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Joolz");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 300);
        contentValues.put(PRODUCT_PIC, R.drawable.pic301); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 352);
        contentValues.put(PRODUCTS_STOCK, 442);
        contentValues.put(PRODUCTS_SALES, 327);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Jem");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 301);
        contentValues.put(PRODUCT_PIC, R.drawable.pic302); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 242);
        contentValues.put(PRODUCTS_STOCK, 361);
        contentValues.put(PRODUCTS_SALES, 371);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jem");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 302);
        contentValues.put(PRODUCT_PIC, R.drawable.pic303); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 422);
        contentValues.put(PRODUCTS_STOCK, 472);
        contentValues.put(PRODUCTS_SALES, 352);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Joolz");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 303);
        contentValues.put(PRODUCT_PIC, R.drawable.pic304); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 132);
        contentValues.put(PRODUCTS_STOCK, 443);
        contentValues.put(PRODUCTS_SALES, 463);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Adidas");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 304);
        contentValues.put(PRODUCT_PIC, R.drawable.pic305); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 45);
        contentValues.put(PRODUCTS_STOCK, 74);
        contentValues.put(PRODUCTS_SALES, 632);
        contentValues.put(PRODUCTS_MAGNITUDE, "XS");
        contentValues.put(PRODUCTS_BRANDS, "Joules");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 305);
        contentValues.put(PRODUCT_PIC, R.drawable.pic306); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 643);
        contentValues.put(PRODUCTS_STOCK, 34);
        contentValues.put(PRODUCTS_SALES, 632);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jack Rogers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 306);
        contentValues.put(PRODUCT_PIC, R.drawable.pic307); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 264);
        contentValues.put(PRODUCTS_STOCK, 272);
        contentValues.put(PRODUCTS_SALES, 423);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Johnston & Murphy");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 307);
        contentValues.put(PRODUCT_PIC, R.drawable.pic308); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 75);
        contentValues.put(PRODUCTS_STOCK, 261);
        contentValues.put(PRODUCTS_SALES, 371);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Janod");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 308);
        contentValues.put(PRODUCT_PIC, R.drawable.pic309); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 132);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 273);
        contentValues.put(PRODUCTS_MAGNITUDE, "L");
        contentValues.put(PRODUCTS_BRANDS, "Jellycat");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 309);
        contentValues.put(PRODUCT_PIC, R.drawable.pic310); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 231);
        contentValues.put(PRODUCTS_STOCK, 381);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Joe's");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 310);
        contentValues.put(PRODUCT_PIC, R.drawable.pic311); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shirts");
        contentValues.put(PRODUCTS_PRICE, 251);
        contentValues.put(PRODUCTS_STOCK, 371);
        contentValues.put(PRODUCTS_SALES, 271);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Jack Rogers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 2);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 311);
        contentValues.put(PRODUCT_PIC, R.drawable.pic312); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 453);
        contentValues.put(PRODUCTS_STOCK, 563);
        contentValues.put(PRODUCTS_SALES, 381);
        contentValues.put(PRODUCTS_MAGNITUDE, "XS");
        contentValues.put(PRODUCTS_BRANDS, "Janod");
        contentValues.put(PRODUCTS_CATEGORY_ID, 1);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 312);
        contentValues.put(PRODUCT_PIC, R.drawable.pic313); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 363);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "17");
        contentValues.put(PRODUCTS_BRANDS, "Nike");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 313);
        contentValues.put(PRODUCT_PIC, R.drawable.pic314); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 362);
        contentValues.put(PRODUCTS_STOCK, 472);
        contentValues.put(PRODUCTS_SALES, 373);
        contentValues.put(PRODUCTS_MAGNITUDE, "21");
        contentValues.put(PRODUCTS_BRANDS, "SuaR");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 314);
        contentValues.put(PRODUCT_PIC, R.drawable.pic315); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 262);
        contentValues.put(PRODUCTS_STOCK, 281);
        contentValues.put(PRODUCTS_SALES, 371);
        contentValues.put(PRODUCTS_MAGNITUDE, "14");
        contentValues.put(PRODUCTS_BRANDS, "Balenciaga");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 315);
        contentValues.put(PRODUCT_PIC, R.drawable.pic316); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 752);
        contentValues.put(PRODUCTS_STOCK, 232);
        contentValues.put(PRODUCTS_SALES, 271);
        contentValues.put(PRODUCTS_MAGNITUDE, "17");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 316);
        contentValues.put(PRODUCT_PIC, R.drawable.pic317); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 123);
        contentValues.put(PRODUCTS_STOCK, 328);
        contentValues.put(PRODUCTS_SALES, 313);
        contentValues.put(PRODUCTS_MAGNITUDE, "7");
        contentValues.put(PRODUCTS_BRANDS, "Jem");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 317);
        contentValues.put(PRODUCT_PIC, R.drawable.pic318); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 352);
        contentValues.put(PRODUCTS_STOCK, 262);
        contentValues.put(PRODUCTS_SALES, 273);
        contentValues.put(PRODUCTS_MAGNITUDE, "14");
        contentValues.put(PRODUCTS_BRANDS, "Janod");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 318);
        contentValues.put(PRODUCT_PIC, R.drawable.pic319); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 356);
        contentValues.put(PRODUCTS_STOCK, 261);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jack Rogers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 9);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 319);
        contentValues.put(PRODUCT_PIC, R.drawable.pic320); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 131);
        contentValues.put(PRODUCTS_STOCK, 261);
        contentValues.put(PRODUCTS_SALES, 222);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Marvel");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 320);
        contentValues.put(PRODUCT_PIC, R.drawable.pic321); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Crocs");
        contentValues.put(PRODUCTS_PRICE, 242);
        contentValues.put(PRODUCTS_STOCK, 361);
        contentValues.put(PRODUCTS_SALES, 262);
        contentValues.put(PRODUCTS_MAGNITUDE, "14");
        contentValues.put(PRODUCTS_BRANDS, "Justice League DC");
        contentValues.put(PRODUCTS_CATEGORY_ID, 3);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 321);
        contentValues.put(PRODUCT_PIC, R.drawable.pic322); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 252);
        contentValues.put(PRODUCTS_STOCK, 361);
        contentValues.put(PRODUCTS_SALES, 371);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Marvel");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 322);
        contentValues.put(PRODUCT_PIC, R.drawable.pic323); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 241);
        contentValues.put(PRODUCTS_STOCK, 241);
        contentValues.put(PRODUCTS_SALES, 362);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jack Rogers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 323);
        contentValues.put(PRODUCT_PIC, R.drawable.pic324); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 362);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 347);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jellycat");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 324);
        contentValues.put(PRODUCT_PIC, R.drawable.pic325); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 123);
        contentValues.put(PRODUCTS_STOCK, 362);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jem");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 325);
        contentValues.put(PRODUCT_PIC, R.drawable.pic326); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 75);
        contentValues.put(PRODUCTS_STOCK, 382);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "New Choice");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 326);
        contentValues.put(PRODUCT_PIC, R.drawable.pic327); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Bags");
        contentValues.put(PRODUCTS_PRICE, 35);
        contentValues.put(PRODUCTS_STOCK, 272);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "New Choice");
        contentValues.put(PRODUCTS_CATEGORY_ID, 4);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 327);
        contentValues.put(PRODUCT_PIC, R.drawable.pic328); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 23);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "New Choice");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 328);
        contentValues.put(PRODUCT_PIC, R.drawable.pic329); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 262);
        contentValues.put(PRODUCTS_STOCK, 275);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jem");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 329);
        contentValues.put(PRODUCT_PIC, R.drawable.pic330); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 123);
        contentValues.put(PRODUCTS_STOCK, 42);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jack Rogers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 330);
        contentValues.put(PRODUCT_PIC, R.drawable.pic331); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Male Undergarments");
        contentValues.put(PRODUCTS_PRICE, 253);
        contentValues.put(PRODUCTS_STOCK, 322);
        contentValues.put(PRODUCTS_SALES, 263);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Joolz");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 331);
        contentValues.put(PRODUCT_PIC, R.drawable.pic332); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Male Undergarments");
        contentValues.put(PRODUCTS_PRICE, 232);
        contentValues.put(PRODUCTS_STOCK, 263);
        contentValues.put(PRODUCTS_SALES, 322);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Jellycat");
        contentValues.put(PRODUCTS_CATEGORY_ID, 10);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 332);
        contentValues.put(PRODUCT_PIC, R.drawable.pic333); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 462);
        contentValues.put(PRODUCTS_STOCK, 383);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Joules");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 333);
        contentValues.put(PRODUCT_PIC, R.drawable.pic334); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 132);
        contentValues.put(PRODUCTS_STOCK, 74);
        contentValues.put(PRODUCTS_SALES, 437);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 334);
        contentValues.put(PRODUCT_PIC, R.drawable.pic335); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Pants");
        contentValues.put(PRODUCTS_PRICE, 183);
        contentValues.put(PRODUCTS_STOCK, 283);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Joe's");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 335);
        contentValues.put(PRODUCT_PIC, R.drawable.pic336); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 242);
        contentValues.put(PRODUCTS_STOCK, 362);
        contentValues.put(PRODUCTS_SALES, 272);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 336);
        contentValues.put(PRODUCT_PIC, R.drawable.pic337); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 242);
        contentValues.put(PRODUCTS_STOCK, 262);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "XL");
        contentValues.put(PRODUCTS_BRANDS, "Jellycat");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 337);
        contentValues.put(PRODUCT_PIC, R.drawable.pic338); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 252);
        contentValues.put(PRODUCTS_STOCK, 234);
        contentValues.put(PRODUCTS_SALES, 272);
        contentValues.put(PRODUCTS_MAGNITUDE, "XS");
        contentValues.put(PRODUCTS_BRANDS, "Joolz");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 338);
        contentValues.put(PRODUCT_PIC, R.drawable.pic339); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shorts");
        contentValues.put(PRODUCTS_PRICE, 374);
        contentValues.put(PRODUCTS_STOCK, 386);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Tommy Hilfiger");
        contentValues.put(PRODUCTS_CATEGORY_ID, 7);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 339);
        contentValues.put(PRODUCT_PIC, R.drawable.pic340); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Blouses");
        contentValues.put(PRODUCTS_PRICE, 573);
        contentValues.put(PRODUCTS_STOCK, 371);
        contentValues.put(PRODUCTS_SALES, 323);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 340);
        contentValues.put(PRODUCT_PIC, R.drawable.pic341); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Blouses");
        contentValues.put(PRODUCTS_PRICE, 362);
        contentValues.put(PRODUCTS_STOCK, 261);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Joolz");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 341);
        contentValues.put(PRODUCT_PIC, R.drawable.pic342); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Blouses");
        contentValues.put(PRODUCTS_PRICE, 463);
        contentValues.put(PRODUCTS_STOCK, 271);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Joules");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 342);
        contentValues.put(PRODUCT_PIC, R.drawable.pic343); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Blouses");
        contentValues.put(PRODUCTS_PRICE, 463);
        contentValues.put(PRODUCTS_STOCK, 273);
        contentValues.put(PRODUCTS_SALES, 372);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Jem");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 343);
        contentValues.put(PRODUCT_PIC, R.drawable.pic344); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Blouses");
        contentValues.put(PRODUCTS_PRICE, 472);
        contentValues.put(PRODUCTS_STOCK, 123);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Janod");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 344);
        contentValues.put(PRODUCT_PIC, R.drawable.pic345); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Blouses");
        contentValues.put(PRODUCTS_PRICE, 763);
        contentValues.put(PRODUCTS_STOCK, 272);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 345);
        contentValues.put(PRODUCT_PIC, R.drawable.pic346); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Blouses");
        contentValues.put(PRODUCTS_PRICE, 346);
        contentValues.put(PRODUCTS_STOCK, 237);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Jellycat");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 346);
        contentValues.put(PRODUCT_PIC, R.drawable.pic347); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Blouses");
        contentValues.put(PRODUCTS_PRICE, 234);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 472);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Joolz");
        contentValues.put(PRODUCTS_CATEGORY_ID, 6);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 347);
        contentValues.put(PRODUCT_PIC, R.drawable.pic348); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Crocs");
        contentValues.put(PRODUCTS_PRICE, 242);
        contentValues.put(PRODUCTS_STOCK, 482);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "7");
        contentValues.put(PRODUCTS_BRANDS, "Jem");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 348);
        contentValues.put(PRODUCT_PIC, R.drawable.pic349); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 312);
        contentValues.put(PRODUCTS_STOCK, 42);
        contentValues.put(PRODUCTS_SALES, 224);
        contentValues.put(PRODUCTS_MAGNITUDE, "14");
        contentValues.put(PRODUCTS_BRANDS, "Sketchers");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 349);
        contentValues.put(PRODUCT_PIC, R.drawable.pic350); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Shoes");
        contentValues.put(PRODUCTS_PRICE, 123);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "13");
        contentValues.put(PRODUCTS_BRANDS, "UGG");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 350);
        contentValues.put(PRODUCT_PIC, R.drawable.pic351); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Slippers");
        contentValues.put(PRODUCTS_PRICE, 231);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 422);
        contentValues.put(PRODUCTS_MAGNITUDE, "7");
        contentValues.put(PRODUCTS_BRANDS, "Guess");
        contentValues.put(PRODUCTS_CATEGORY_ID, 8);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 351);
        contentValues.put(PRODUCT_PIC, R.drawable.pic352); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 341);
        contentValues.put(PRODUCTS_STOCK, 329);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "M");
        contentValues.put(PRODUCTS_BRANDS, "Joules");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 352);
        contentValues.put(PRODUCT_PIC, R.drawable.pic353); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Hats");
        contentValues.put(PRODUCTS_PRICE, 23);
        contentValues.put(PRODUCTS_STOCK, 414);
        contentValues.put(PRODUCTS_SALES, 381);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Jem");
        contentValues.put(PRODUCTS_CATEGORY_ID, 14);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 353);
        contentValues.put(PRODUCT_PIC, R.drawable.pic354); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Female Undergarments");
        contentValues.put(PRODUCTS_PRICE, 132);
        contentValues.put(PRODUCTS_STOCK, 372);
        contentValues.put(PRODUCTS_SALES, 482);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Joolz");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);

        contentValues.put(PRODUCTS_ID, 354);
        contentValues.put(PRODUCT_PIC, R.drawable.pic355); //Picture zette , maar t is op String
        contentValues.put(PRODUCTS_NAME, "Female Undergarments");
        contentValues.put(PRODUCTS_PRICE, 372);
        contentValues.put(PRODUCTS_STOCK, 324);
        contentValues.put(PRODUCTS_SALES, 382);
        contentValues.put(PRODUCTS_MAGNITUDE, "S");
        contentValues.put(PRODUCTS_BRANDS, "Jem");
        contentValues.put(PRODUCTS_CATEGORY_ID, 19);
        insertOneRecord(PRODUCTS_TABLE, contentValues);
    }

    public void setDefaultCredentialsCategory() {
        //Set default
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_ID, 1);
        contentValues.put(CATEGORY_NAME, "Pants_Shorts_Male");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 2);
        contentValues.put(CATEGORY_NAME, "T-Shirts_Male");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 3);
        contentValues.put(CATEGORY_NAME, "Shoes_Male");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 4);
        contentValues.put(CATEGORY_NAME, "Bags_Male");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 5);
        contentValues.put(CATEGORY_NAME, "Skirts_Female");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 6);
        contentValues.put(CATEGORY_NAME, "Blouses_Female");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 7);
        contentValues.put(CATEGORY_NAME, "Pants_Shorts_Female");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 8);
        contentValues.put(CATEGORY_NAME, "Shoes_Female");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 9);
        contentValues.put(CATEGORY_NAME, "Bags_Female");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 10);
        contentValues.put(CATEGORY_NAME, "Undergarments_Male");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 11);
        contentValues.put(CATEGORY_NAME, "Perfume_Female");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 12);
        contentValues.put(CATEGORY_NAME, "Perfume_Male");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 13);
        contentValues.put(CATEGORY_NAME, "Jewelry");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 14);
        contentValues.put(CATEGORY_NAME, "Hats");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 15);
        contentValues.put(CATEGORY_NAME, "Skirts_Girls");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 16);
        contentValues.put(CATEGORY_NAME, "Pants_Shorts_Boys");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 17);
        contentValues.put(CATEGORY_NAME, "Pants_Shorts_Girls");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 18);
        contentValues.put(CATEGORY_NAME, "Make-up_Female");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);

        contentValues.put(CATEGORY_ID, 19);
        contentValues.put(CATEGORY_NAME, "Undergarments_Female");
        contentValues.put(CATEGORY_TIMES_VIEWED, 0);
        insertOneRecord(CATEGORY_TABLE, contentValues);
    }

}
