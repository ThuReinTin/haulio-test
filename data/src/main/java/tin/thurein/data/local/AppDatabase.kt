package tin.thurein.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import tin.thurein.data.local.daos.JobDao
import tin.thurein.data.local.entities.JobEntity

@Database(entities = arrayOf(JobEntity::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun jobDao(): JobDao
}