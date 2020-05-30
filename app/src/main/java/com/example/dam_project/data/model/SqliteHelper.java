package com.example.dam_project.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "casajuanrestaurante.db";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    /*
     * TABLE USER
     *
     * */

    //TABLE NAME
    public static final String TABLE_USERS = "users";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";

    //COLUMN email
    public static final String KEY_EMAIL = "email";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";


    /* METHODS
     * TABLE USER
     *
     * */

    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in  @values
        values.put(KEY_EMAIL, user.email);

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }


    /*
     * TABLE PRODUCT
     *
     * */

    //TABLE NAME
    public static final String TABLE_PRODUCTS = "products";

    //TABLE PRODUCTS COLUMNS
    //ID COLUMN @primaryKey
    public static final String ID = "id";

    //COLUMN name
    public static final String NAME = "name";

    //COLUMN description
    public static final String DESCRIPTION = "description";

    //COLUMN category
    public static final String CATEGORY = "category";

    //COLUMN float
    public static final String PRIZE = "prize";

    //COLUMN avatar
    public static final String AVATAR_URI = "avatarUri";


    public static final String SQL_TABLE_PRODUCTS = "CREATE TABLE " + ProductContract.ProductEntry.TABLE_NAME + " ("
            + ProductContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ProductContract.ProductEntry.ID + " TEXT NOT NULL,"
            + ProductContract.ProductEntry.NAME + " TEXT NOT NULL,"
            + ProductContract.ProductEntry.DESCRIPTION + " TEXT NOT NULL,"
            + ProductContract.ProductEntry.CATEGORY + " TEXT NOT NULL,"
            + ProductContract.ProductEntry.PRIZE + " TEXT NOT NULL,"
            + ProductContract.ProductEntry.AVATAR_URI + " TEXT,"
            + "UNIQUE (" + ProductContract.ProductEntry.ID + "))";


    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_PRODUCTS);
        mockData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_PRODUCTS);

    }

    /* METHODS
     * TABLE PRODUCT
     *
     * */

    public long mockProduct(SQLiteDatabase db, Product product) {
        return db.insert(
                ProductContract.ProductEntry.TABLE_NAME,
                null,
                product.toContentValues());
    }



    public long saveProduct(Product product) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                ProductContract.ProductEntry.TABLE_NAME,
                null,
                product.toContentValues());

    }

    public Cursor getAllProducts() {
        return getReadableDatabase()
                .query(
                        ProductContract.ProductEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getProductById(String productId) {
        Cursor c = getReadableDatabase().query(
                ProductContract.ProductEntry.TABLE_NAME,
                null,
                ProductContract.ProductEntry.ID + " LIKE ?",
                new String[]{productId},
                null,
                null,
                ProductContract.ProductEntry.NAME+" ASC");
        return c;
    }

    //Filter to get products by Category
    public Cursor getProductByCategory(String category) {
        Cursor c = getReadableDatabase().query(
                ProductContract.ProductEntry.TABLE_NAME,
                null,
                ProductContract.ProductEntry.CATEGORY+ " LIKE ?",
                new String[]{category},
                null,
                null,
                ProductContract.ProductEntry.NAME+" ASC");
        return c;
    }

    public int deleteProduct(String productId) {
        return getWritableDatabase().delete(
                ProductContract.ProductEntry.TABLE_NAME,
                ProductContract.ProductEntry.ID + " LIKE ?",
                new String[]{productId});
    }

    public int updateProduct(Product product, String productId) {
        return getWritableDatabase().update(
                ProductContract.ProductEntry.TABLE_NAME,
                product.toContentValues(),
                ProductContract.ProductEntry.ID + " LIKE ?",
                new String[]{productId}
        );
    }

    //PRODUCTS MENU
    private void mockData(SQLiteDatabase sqLiteDatabase) {

        mockProduct(sqLiteDatabase, new Product("Jamón Iberico de Bellota-Reserva D.O", "Prime Acorn-fed Iberian Cured Ham",
                "Entrantes", "16",  "jamonibe.jpg"));
        mockProduct(sqLiteDatabase, new Product("Surtido de Ibéricos Jamón, Caña de Lomo y Cecina ", "Selection of Prime Acorn-fed Iberian Cured Meat",
                "Entrantes", "17",  "surtidoibe.jpg"));
        mockProduct(sqLiteDatabase, new Product("Chorizos a la Sidra con patatinas al pimentón ", "Asturian cooked Chorizo sausage",
                "Entrantes",  "8",  "chorisidra.jpg"));
        mockProduct(sqLiteDatabase, new Product("Sartén Huevos rotos, patatas fritas, picadillo de chorizo y Padrón", "Scrambled eggs with fried chips, minced Chorizo sausage & green peppers",
                "Entrantes", "10",  "sartenhue.jpg"));
        mockProduct(sqLiteDatabase, new Product("Pulpo a la Gallega con patatinas y pimentón", "Boiled ‘Galician style’ Octopus, served over bed of boiled potatoes & paprika",
                "Entrantes", "15",  "pulpo.jpg"));
        mockProduct(sqLiteDatabase, new Product("Empanada de Atún hecha en casa", "Home made white Tuna pie",
                "Entrantes", "10",  "empanada.jpg"));
        mockProduct(sqLiteDatabase, new Product("Tartare de Atún Rojo y Aguacate", "Fresh Red Tuna & Avocado Tartare",
                "Entrantes", "12",  "tartar.jpg"));
        mockProduct(sqLiteDatabase, new Product("Croquetas de Jamón", "Ham Croquettes",
                "Entrantes", "12",  "croquetas.jpg"));



        mockProduct(sqLiteDatabase, new Product("Espinaca fresca, queso de cabra, beicon frito y nueces al balsámico ", "Fresh Spinach, goat cheese, fried bacon, nuts & balsamic Modena vinegar",
                "Ensaladas", "11",  "espinaca_ensalada.jpg"));
        mockProduct(sqLiteDatabase, new Product("Ventresca de Bonito en aceite, cogollitos, pimientos rojos asados ", "White Tuna breast fillet in Olive oil with cos lettuce heart & sweet red pepper",
                "Ensaladas", "12",  "ventresca.jpg"));
        mockProduct(sqLiteDatabase, new Product("Centollo con Aguacate y Salsa Rosa", "King Crab & Avocado Salad in pink sauce dressing",
                "Ensaladas",  "15",  "cangrejo_ensalada.jpg"));
        mockProduct(sqLiteDatabase, new Product("Salmón marinado al eneldo", "Marinated Salmon with dill ‘Gravlax’ mustard",
                "Ensaladas", "18",  "salmon.jpg"));
        mockProduct(sqLiteDatabase, new Product("Verduras salteadas con arroz", "Sauteed Vegetables with rice ",
                "Ensaladas", "12",  "versalteadas.jpg"));
        mockProduct(sqLiteDatabase, new Product("Ensalada de Garbanzos marinados y queso de cabra", "Marinated chickpea and goat cheese salad",
                "Ensaladas", "9",  "garensalada.jpg"));
        mockProduct(sqLiteDatabase, new Product("Pastel de puerros", "Leeks Pie",
                "Ensaladas", "10",  "pastelpuerros.jpg"));

        mockProduct(sqLiteDatabase, new Product("Pierna de Cordero Lechal al horno, patatas fritas y verduras", "Roast Shoulder of Suckling Lamb with mint sauce, chips & vegetables",
                "Carnes", "23",  "cordero.jpg"));
        mockProduct(sqLiteDatabase, new Product("Solomillo con patatas fritas y verduras de temporada", "BBQ Fillet Steak with chips & vegetables",
                "Carnes", "25",  "solomillo.jpg"));
        mockProduct(sqLiteDatabase, new Product("Solomillo con Foie Gras fresco de pato a la plancha ", "BBQ Fillet Steak with Fresh Duck ‘Foie Gras’ with chips & vegetables",
                "Carnes", "28",  "solomillofoie.jpg"));
        mockProduct(sqLiteDatabase, new Product("Chuletón (1 Kg.) con patatas fritas y pimiento Padrón", "BBQ Ribeye Steak Loin Chop (1 Kg.) with chips & baby fried green peppers",
                "Carnes",  "42",  "chuleton.jpg"));
        mockProduct(sqLiteDatabase, new Product("Cachopo de Ternera Asturiana relleno de jamón y queso", "Asturian beef cachopo stuffed with ham and cheese",
                "Carnes",  "25",  "cachopoter.jpg"));
        mockProduct(sqLiteDatabase, new Product("Entrecot, patatas fritas y verduras de temporada ", "BBQ Sirloin Beef Entrecôte with chips & vegetables",
                "Carnes", "26",  "entrecot.jpg"));
        mockProduct(sqLiteDatabase, new Product("Escalopines en salsa Cabrales con patatas", "Scallops in Cabrales sauce with potatoes",
                "Carnes", "14",  "escalopes.jpg"));
        mockProduct(sqLiteDatabase, new Product("Fabada Asturiana con su compango (pringá): chorizo, morcilla y lacón. ", "Original Bean Stew with side dish of Chorizo sausage & black puddin",
                "Carnes", "19",  "fabadaastur.jpg"));
        mockProduct(sqLiteDatabase, new Product("Albóndigas de la Casa en salsa de almendras y patatas fritas ", "Home made Meat Balls in almond sauce & chips",
                "Carnes", "11",  "albondigas.jpg"));
        mockProduct(sqLiteDatabase, new Product("Hamburguesa (180 gr.) con queso y patatas paja", "Beef Burger (180 gr.) with cheese & thin crispy fries",
                "Carnes", "14",  "hamburguesa.jpg"));
        mockProduct(sqLiteDatabase, new Product("Rabo de Toro en taco y deshuesado, patatas fritas y verduras ", "Oxtail Stew off the bone in almond sauce with chips & vegetables",
                "Carnes", "18",  "rabo.jpg"));

        mockProduct(sqLiteDatabase, new Product("Rollo de Bonito de Cudillero con salsa de tomate y verduras", "White Tuna pie with tomato sauce & vegetables",
                "Pescado", "12",  "rollobonito.jpg"));
        mockProduct(sqLiteDatabase, new Product("Salmón a la crema de champiñones con patatitas y verduras", "Grilled Salmon in mushroom sauce with boiled potatoes & vegetables",
                "Pescado", "15",  "salmoncham.jpg"));
        mockProduct(sqLiteDatabase, new Product("Lomo de Atún Rojo a la plancha sobre verduras de temporada", "Grilled fillet of Red Tuna served with vegetables",
                "Pescado", "18",  "atunrojo.jpg"));
        mockProduct(sqLiteDatabase, new Product("Ventresca de Atún a la plancha sobre verduras de temporada", "Grilled White Tuna breast served with vegetables",
                "Pescado",  "23",  "ventrescaatun.jpg"));
        mockProduct(sqLiteDatabase, new Product("Bacalao al Pil-Pil con ajos confitados y manzana", "Grilled prime Cod confit ‘Pil-Pil-Style’ with whole garlic & apple",
                "Pescado", "21",  "bacalao.jpg"));
        mockProduct(sqLiteDatabase, new Product("Merluza de anzuelo a la Sidra con almejas de carril", "Hake Fillet in cider sauce with clams",
                "Pescado", "19",  "merluza.jpg"));

        mockProduct(sqLiteDatabase, new Product("Arroz con leche", "Rice pudding",
                "Postres", "8",  "arrozconleche.jpg"));
        mockProduct(sqLiteDatabase, new Product("Frixuelos rellenos de chocolate", "Frixuelos filled with chocolate",
                "Postres", "10",  "frixuelos.jpg"));
        mockProduct(sqLiteDatabase, new Product("Tarta de queso", "Cheesecake",
                "Postres", "11",  "tartaqueso.jpg"));
        mockProduct(sqLiteDatabase, new Product("Tarta de manzana", "Apple pie",
                "Postres",  "8",  "tartamanzana.jpg"));
        mockProduct(sqLiteDatabase, new Product("Brownie de chocolate con helado", "Chocolate Brownie with ice cream",
                "Postres", "9",  "brownie.jpg"));
        mockProduct(sqLiteDatabase, new Product("Tarta de oreo casera", "Home made Oreo Pie",
                "Postres", "12",  "oreocake.jpg"));
        mockProduct(sqLiteDatabase, new Product("Macedonia de fruta fresca de temporada", "Fresh Fruit",
                "Postres", "7",  "macedonia.jpg"));


        mockProduct(sqLiteDatabase, new Product("Vino tinto Pétalos del Bierzo", "Petalos del Bierzo red wine",
                "Bebidas", "22",  "vinopetalos.jpg"));
        mockProduct(sqLiteDatabase, new Product("Vino tinto Tejoneras 2010", "Tejoneras 2010 red wine",
                "Bebidas", "35",  "vinotejoneras.jpg"));
        mockProduct(sqLiteDatabase, new Product("Sidra Trabanco", "Trabanco sider",
                "Bebidas",  "8",  "sidra.jpg"));
        mockProduct(sqLiteDatabase, new Product("Vino blanco Fuente Elvira 2016" , "Fuente Elvira 2016 White wine",
                "Bebidas", "17",  "fuentelvira.jpg"));
        mockProduct(sqLiteDatabase, new Product("Vino Oporto Pedro Ximenez", "Pedro Ximenez Oporto wine",
                "Bebidas", "3.5",  "pedroximenez.jpg"));
        mockProduct(sqLiteDatabase, new Product("Champagne Billecart-Salmon Brut Rosé", "Billecart-Salmon Brut Rosé Champagne",
                "Bebidas", "85",  "champagnebille.jpg"));

    }

}
