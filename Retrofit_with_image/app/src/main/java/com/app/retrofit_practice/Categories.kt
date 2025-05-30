import java.util.Locale.Category

data class Categories(
    val data : CategoryData
)
data class CategoryData(
            val data : List<CategoryItem>
        )
data class CategoryItem(
    val name: String,
    val description: String?,
    val icon: String
)
