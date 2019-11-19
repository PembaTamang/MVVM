package pembatamang.mvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1,exportSchema = false)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun getNoteDao(): NoteDao
    companion object {
        @Volatile
        private var instance: NoteDatabase? = null
        private val Lock = Any()
        fun getInstance(c: Context) = instance
            ?: synchronized(Lock) {
                instance
                    ?: buildDB(c).also {
                        instance = it
                    }
            }

        private fun buildDB(c: Context): NoteDatabase {
            val dbName = "notedatabse.db"
            return Room.databaseBuilder(c.applicationContext, NoteDatabase::class.java, dbName)
                .addMigrations(migration)
                .build()
        }

        private val migration: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
    }
}