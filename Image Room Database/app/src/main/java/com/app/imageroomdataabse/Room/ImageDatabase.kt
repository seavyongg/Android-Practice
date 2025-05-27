package com.app.imageroomdataabse.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.imageroomdataabse.Model.Images

@Database(
    entities = [Images::class],
    version = 1,
    exportSchema = false //to avoid the error of schema
)
abstract class ImageDatabase : RoomDatabase() {
    //1->annotation
    //2->abstract and extends RoomDatabase
    //3->abstract method returns Dao instance


    abstract fun imageDao(): ImageDao

    //singleton design pattern
    companion object {
        //Volatile -> to make sure that the value of the variable is always up to date
        @Volatile
        private var instance: ImageDatabase? = null

        //context represents the current state of the application, used to access resorce, database, service, etc.
        //why pass context? Needed to initialize the database(e.g, to access app-specific storage)
        fun getDataseInstance(context: Context): ImageDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, //applicationContext -> to avoid memory leaks , recommended to use application context not activity context
                        ImageDatabase::class.java, //it is good for long-lived objects like dataabase.
                        "image_database"
                    ).fallbackToDestructiveMigration()
                        .build() //build the database
                }
                return instance as ImageDatabase
            }
        }
    }
}
//@Database(
//    entities = [Images::class],
//    version = 1
//)
//
//abstract class ImageDatabase : RoomDatabase(){
//    abstract fun imageDao(): ImageDao
//    companion object{
//        @Volatile
//        private var instance: ImageDatabase? = null
//
//        fun getDataseInstance(context: Context): ImageDatabase {
//            synchronized(this){
//                if(instance == null ){
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        ImageDatabase::class.java,
//                        "image_database"
//                    ).build()
//                }
//            }
//            return instance as ImageDatabase
//        }
//
//        }
//}