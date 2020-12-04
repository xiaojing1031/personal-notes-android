# Jetpack 主要框架 & 组件
## ViewModel 组件
- 用于存放与界面相关的数据
- 随着Activity的销毁（onDestroy() ）而销毁

## Lifecycles 组件 
- 用来感知Activity生命周期
- LifeCycleObserver

## LiveData
- 响应式编程组件
- 存放任意类型数据，并在数据发生变化时通知给Observer
- 大多数时候和ViewModel结合使用

## ORM 框架
object relational mapping 对象关系映射
将面向对象的语言和面向关系的数据库之间建立一种映射关系

Room：
- Entity：用于定义封装实际数据的实体类，每个实体类都会在数据库中有一张对应的表，并且表中的列是根据实体类中的字段自动生成 
```
@Entity(tableName = "user_table")
data class User (
    var id: String 
    
    var name: String
)
```
- Dao：用来对数据库各种操作进行封装：用来和逻辑层交互
```
@Dao
interface UserDao {
    @Insert 
    fun insertUser(user: User): Long
    
    @Delete
    fun deleteUser(user: User)
    
    @Update
    @Query
}
```
- Database：用于定义数据库中的关键信息，包括数据库版本号、包含哪些实体类以及提供Dao层的访问实例
```
@Database(versin= 2, entities = [User::Class])
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    
    companion object {
        const val DB_NAME = "user_database"
    }
}
```
- 数据库升级
（如果用fallbackToDestructiveMigration会自动升级但是之前的数据会丢失）
```
object DataUpdate {
    
    val db by lazy {
        Room.databseBuilder(context, 
            UserDatabase::class.java, UserDatabase.DB_NAME).addMigrations(MIGRATION_1_2, MIGRATION_2_3,...).build()
    }
    
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE `user_table` ADD COLUMN `name` TEXT")
        
        }
    }
}
```
