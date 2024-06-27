package com.mustfaibra.roffu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mustfaibra.roffu.R
import com.mustfaibra.roffu.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        Advertisement::class,
        Manufacturer::class,
        Review::class,
        User::class,
        PaymentProvider::class,
        UserPaymentProvider::class,
        Product::class,
        BookmarkItem::class,
        Location::class,
        CartItem::class,
        Order::class,
        OrderItem::class,
        OrderPayment::class,
        Notification::class,
        ProductColor::class,
        ProductSize::class,
    ],
    version = 1, exportSchema = false)
abstract class RoomDb : RoomDatabase() {

    /** A function that used to retrieve Room's related dao instance */
    abstract fun getDao(): RoomDao

    class PopulateDataClass @Inject constructor(
        private val client: Provider<RoomDb>,
        private val scope: CoroutineScope,
    ) : RoomDatabase.Callback() {
        private val description =
            "This is the description text that is supposed to be long enough to show how the UI looks, so it's not a real text.\n"
        private val manufacturers = listOf(
            Manufacturer(id = 1, name = "Nike", icon = R.drawable.ic_nike),
            Manufacturer(id = 2, name = "Adidas", icon = R.drawable.adidas_48),
            Manufacturer(id = 3, name = "New Balance", icon = R.drawable.new_balance),
            Manufacturer(id = 4, name = "Duz Taban", icon = R.drawable.duz_taban),
        )
        private val advertisements = listOf(
            Advertisement(1, R.drawable.air_huarache_gold_black_ads, 1, 0),
            Advertisement(2, R.drawable.pegasus_trail_gortex_ads, 2, 0),
            Advertisement(3, R.drawable.blazer_low_black_ads, 3, 0),
        )
        private val nikeProducts = listOf(
            Product(
                id = 1,
                name = "Pegasus Trail Gortex Green",
                image = R.drawable.pegasus_trail_3_gore_tex_dark_green,
                price = 149.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "dark-green",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.pegasus_trail_3_gore_tex_lemon),
                )
            },
            Product(
                id = 3,
                name = "Air Huarache Gold",
                image = R.drawable.air_huarache_le_gold_black,
                price = 159.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "gold",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.air_huarache_le_gray_dark),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.air_huarache_le_pink_black),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.air_huarache_le_red_black),
                )
            },
            Product(
                id = 7,
                name = "Blazer Low Black",
                image = R.drawable.blazer_low_black,
                price = 120.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "black",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.blazer_low_pink),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.blazer_low_light_green),
                )
            },
            // Adding a new Nike shoe


        )
        private val adidasProducts = listOf(
            Product(
                id = 10,
                name = "Defiant Generation Green",
                image = R.drawable.defiant_generation_green,
                price = 149.0,
                description = description,
                manufacturerId = 2,
                basicColorName = "green",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.defiant_generation_red),
                )
            },

            Product(
                id = 12,
                name = "Solarthon Primegreen Gray",
                image = R.drawable.solarthon_primegreen_gray,
                price = 159.0,
                description = description,
                manufacturerId = 2,
                basicColorName = "gray",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "black",
                        image = R.drawable.solarthon_primegreen_black),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.solarthon_primegreen_red),
                )
            },

            // Adding a new Nike shoe

        )
        private val newBalanceProducts = listOf(
            Product(
                id = 15,
                name = "Fresh Foam 1080v11",
                image = R.drawable.fresh_foam,
                price = 149.0,
                description = description,
                manufacturerId = 3,
                basicColorName = "blue",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.fresh_foam_1080v11_gray),
                )
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            },
            // Adding two more New Balance shoes
            Product(
                id = 16,
                name = "FuelCell Rebel v2",
                image = R.drawable.fuelcell_rebel_v2,
                price = 130.0,
                description = description,
                manufacturerId = 3,
                basicColorName = "yellow",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "blue",
                        image = R.drawable.fuelcell_rebel_v2_blue),
                )
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            },
            Product(
                id = 17,
                name = "Fresh Foam More v3",
                image = R.drawable.fresh_foam_more_v3,
                price = 140.0,
                description = description,
                manufacturerId = 3,
                basicColorName = "white",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "black",
                        image = R.drawable.fresh_foam_more_v3_black),
                )
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            },
        )
        private val duzTabanProducts = listOf(
            Product(
                id = 18,
                name = "Pegasus Trail Gortex Green",
                image = R.drawable.pegasus_trail_3_gore_tex_dark_green,
                price = 149.0,
                description = description,
                manufacturerId = 4,
                basicColorName = "dark-green",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.pegasus_trail_3_gore_tex_lemon),
                )
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            },
            Product(
                id = 19,
                name = "Defiant Generation Green",
                image = R.drawable.defiant_generation_green,
                price = 149.0,
                description = description,
                manufacturerId = 4,
                basicColorName = "green",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.defiant_generation_red),
                )
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            },
            Product(
                id = 20,
                name = "Fresh Foam 1080v11",
                image = R.drawable.fresh_foam,
                price = 149.0,
                description = description,
                manufacturerId = 4,
                basicColorName = "blue",
            ).also {
                it.colors = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.fresh_foam_1080v11_gray),
                )
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            },
        )
        private val paymentProviders = listOf(
            PaymentProvider(
                id = "apple",
                title = R.string.apple_pay,
                icon = R.drawable.ic_apple,
            ),
            PaymentProvider(
                id = "master",
                title = R.string.master_card,
                icon = R.drawable.ic_master_card,
            ),
            PaymentProvider(
                id = "visa",
                title = R.string.visa,
                icon = R.drawable.ic_visa,
            ),
        )
        private val userPaymentAccounts = listOf(
            UserPaymentProvider(
                providerId = "apple",
                cardNumber = "8402-5739-2039-5780"
            ),
            UserPaymentProvider(
                providerId = "master",
                cardNumber = "3323-8202-4748-2002"
            ),
            UserPaymentProvider(
                providerId = "visa",
                cardNumber = "7483-02836-4839-2855"
            ),
        )
        private val userLocation = Location(
            address = "44 Magtymguly Shayoli, Ashgabat",
            city = "Ashgabat",
            country = "Turkmenistan"
        )

        init {
            nikeProducts.onEach {
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            }
            adidasProducts.onEach {
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            }
            newBalanceProducts.onEach {
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            }

            scope.launch {
                populateDatabase(dao = client.get().getDao(), scope = scope)
            }
        }

        private suspend fun populateDatabase(dao: RoomDao, scope: CoroutineScope) {
            /** Save users */
            scope.launch {
                dao.saveUser(
                    User(
                        userId = 1,
                        name = "Nurmahhmet Yazjumayev",
                        profile = R.drawable.profile_pic,
                        phone = "20040529",
                        email = "Nurmuhamet@gmail.com",
                        password = "12345",
                        token = "ds2f434ls2ks2lsj2ls",
                    )
                )
            }
            /** insert manufacturers */
            scope.launch {
                manufacturers.forEach {
                    dao.insertManufacturer(it)
                }
            }
            /** insert advertisements */
            scope.launch {
                advertisements.forEach {
                    dao.insertAdvertisement(it)
                }
            }
            /** Insert products */
            scope.launch {
                nikeProducts.plus(adidasProducts).plus(newBalanceProducts).plus(duzTabanProducts).forEach {
                    /** Insert the product itself */
                    dao.insertProduct(product = it)
                    /** Insert colors */
                    it.colors?.forEach { productColor ->
                        dao.insertOtherProductCopy(productColor)
                    }
                    /** Insert size */
                    it.sizes?.forEach { productSize ->
                        dao.insertSize(productSize)
                    }
                }
            }
            /** Insert payment providers */
            scope.launch {
                paymentProviders.forEach {
                    dao.savePaymentProvider(paymentProvider = it)
                }
            }
            /** Insert user's payment providers */
            scope.launch {
                userPaymentAccounts.forEach {
                    dao.saveUserPaymentProvider(it)
                }
            }
            /** Insert user's location */
            scope.launch {
                dao.saveLocation(location = userLocation)
            }
        }
    }

}
