package pt.ipp.estgf.tourguide.BaseDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vitor on 30/11/2015.
 */
public class SQLiteConnect extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteConnect (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_category(category_name VARCHAR(75) PRIMARY KEY, icon VARCHAR(20))");
        db.execSQL("CREATE TABLE tbl_poi(id_poi INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(75), description VARCHAR(255), latitude VARCHAR(15), longitude VARCHAR(15), rating INTEGER, category_name VARCHAR(75))");
        db.execSQL("CREATE TABLE tbl_photo(id_photo INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(75), description VARCHAR(255), path VARCHAR(75))");
        db.execSQL("CREATE TABLE tbl_tour(id_tour INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(75), description VARCHAR(255))");
        db.execSQL("CREATE TABLE tbl_tour_pois(id_tour INTEGER, id_poi INTEGER)");

        //Inserir Dados
        db.execSQL("INSERT INTO tbl_category(category_name,icon) VALUES ('Praias','ic_categoria_praia')");
        db.execSQL("INSERT INTO tbl_category(category_name,icon) VALUES ('Monumentos','ic_categoria_monumento')");
        db.execSQL("INSERT INTO tbl_category(category_name,icon) VALUES ('Parques','ic_categoria_parque')");
        db.execSQL("INSERT INTO tbl_category(category_name,icon) VALUES ('Escolas','ic_categoria_escola')");
        db.execSQL("INSERT INTO tbl_category(category_name,icon) VALUES ('Casinos','ic_categoria_casino')");
        db.execSQL("INSERT INTO tbl_category(category_name,icon) VALUES ('Empresas','ic_categoria_empresa')");
        db.execSQL("INSERT INTO tbl_category(category_name,icon) VALUES ('Bancos','ic_categoria_banco')");
        db.execSQL("INSERT INTO tbl_category(category_name,icon) VALUES ('Multibancos','ic_categoria_mb')");

        db.execSQL("INSERT INTO tbl_poi(name,description,latitude,longitude,rating,category_name) VALUES ('ESTGF','Melhor de todas','41.3666961132','-8.19499611855','5','Escolas')");
        db.execSQL("INSERT INTO tbl_poi(name,description,latitude,longitude,rating,category_name) VALUES ('ISEP','Escola do IPP','41.17744066702602','-8.609172105789185','4','Escolas')");
        db.execSQL("INSERT INTO tbl_poi(name,description,latitude,longitude,rating,category_name) VALUES ('FEUP','Faculdade de Engenharia Universidade do Porto','41.1780','-8.5980','5','Escolas')");
        db.execSQL("INSERT INTO tbl_poi(name,description,latitude,longitude,rating,category_name) VALUES ('Praia Matosinhos','Praia do porto','41.183',' -8.67977','5','Praias')");
        db.execSQL("INSERT INTO tbl_poi(name,description,latitude,longitude,rating,category_name) VALUES ('Parque da cidade Paredes','ppppp','0.000','0.000','5','Parques')");
        db.execSQL("INSERT INTO tbl_poi(name,description,latitude,longitude,rating,category_name) VALUES ('Parque da cidade Paredes','ppppp','0.000','0.000','5','Parques')");
        db.execSQL("INSERT INTO tbl_poi(name,description,latitude,longitude,rating,category_name) VALUES ('Parque da cidade Paredes','ppppp','0.000','0.000','5','Parques')");
        db.execSQL("INSERT INTO tbl_poi(name,description,latitude,longitude,rating,category_name) VALUES ('Parque da cidade Paredes','ppppp','0.000','0.000','5','Parques')");
        db.execSQL("INSERT INTO tbl_poi(name,description,latitude,longitude,rating,category_name) VALUES ('Parque da cidade Paredes','ppppp','0.000','0.000','5','Parques')");

        db.execSQL("INSERT INTO tbl_tour(name,description) VALUES ('Rota de Entrega Material','Entrega de material para construcoes de casas')");
        db.execSQL("INSERT INTO tbl_tour(name,description) VALUES ('Rota de Sabado','Saida ao sabado')");
        db.execSQL("INSERT INTO tbl_tour(name,description) VALUES ('Rota da familia','Saida ao domingo')");

        db.execSQL("INSERT INTO tbl_tour_pois(id_tour,id_poi) VALUES (1,1)");
        db.execSQL("INSERT INTO tbl_tour_pois(id_tour,id_poi) VALUES (1,2)");
        db.execSQL("INSERT INTO tbl_tour_pois(id_tour,id_poi) VALUES (1,3)");
        db.execSQL("INSERT INTO tbl_tour_pois(id_tour,id_poi) VALUES (1,4)");



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

}