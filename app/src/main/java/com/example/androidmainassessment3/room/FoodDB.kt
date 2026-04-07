package com.example.androidmainassessment3.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidmainassessment3.room.dao.CartDao
import com.example.androidmainassessment3.room.dao.OrderDao
import com.example.androidmainassessment3.room.dao.ProductDao
import com.example.androidmainassessment3.room.dao.ProfileDao
import com.example.androidmainassessment3.room.model.Cart
import com.example.androidmainassessment3.room.model.Order
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile


@Database(entities = [Profile::class, Product::class,Cart::class,Order::class], version = 1)
abstract class FoodDB: RoomDatabase() {

    abstract val profileDao: ProfileDao
    abstract val productDao: ProductDao
    abstract val cartDao: CartDao
    abstract val orderDao: OrderDao


    companion object {

        @Volatile
        var INSTANCE: FoodDB?=null
        fun getInstance(context: Context): FoodDB{

            synchronized(this){
                var instance=INSTANCE

                if(instance==null){
                    instance= Room.databaseBuilder(
                        context=context,
                        FoodDB::class.java,
                        "FoodDB"
                    ).allowMainThreadQueries().build()
                }
                INSTANCE=instance
                return instance
            }

        }


    }
}