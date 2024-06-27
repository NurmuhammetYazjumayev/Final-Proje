package com.mustfaibra.roffu.screens.holder

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.mustfaibra.roffu.components.AppBottomNav
import com.mustfaibra.roffu.components.CustomSnackBar
import com.mustfaibra.roffu.models.CartItem
import com.mustfaibra.roffu.models.User
import com.mustfaibra.roffu.providers.LocalNavHost
import com.mustfaibra.roffu.screens.bookmarks.BookmarksScreen
import com.mustfaibra.roffu.screens.cart.CartScreen
import com.mustfaibra.roffu.screens.checkout.CheckoutScreen
import com.mustfaibra.roffu.screens.home.HomeScreen
import com.mustfaibra.roffu.screens.locationpicker.LocationPickerScreen
import com.mustfaibra.roffu.screens.login.LoginScreen
import com.mustfaibra.roffu.screens.notifications.NotificationScreen
import com.mustfaibra.roffu.screens.onboard.OnboardScreen
import com.mustfaibra.roffu.screens.orderhistory.OrdersHistoryScreen
import com.mustfaibra.roffu.screens.productdetails.ProductDetailsScreen
import com.mustfaibra.roffu.screens.profile.ProfileScreen
import com.mustfaibra.roffu.screens.search.SearchScreen
import com.mustfaibra.roffu.screens.signup.SignupScreen
import com.mustfaibra.roffu.screens.splash.SplashScreen
import com.mustfaibra.roffu.sealed.Screen
import com.mustfaibra.roffu.utils.UserPref
import com.mustfaibra.roffu.utils.getDp
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.launch

@Composable
fun HolderScreen(
    onStatusBarColorChange: (color: Color) -> Unit,
    holderViewModel: HolderViewModel = hiltViewModel(),
) {
    val destinations = remember {
        listOf(Screen.Home, Screen.Notifications, Screen.Bookmark, Screen.Profile)
    }

    val controller = LocalNavHost.current
    val currentRouteAsState = getActiveRoute(navController = controller)
    val cartItems = holderViewModel.cartItems
    val productsOnCartIds = holderViewModel.productsOnCartIds
    val productsOnBookmarksIds = holderViewModel.productsOnBookmarksIds
    val user by UserPref.user.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val (snackBarColor, setSnackBarColor) = remember { mutableStateOf(Color.White) }
    val snackBarTransition = updateTransition(
        targetState = scaffoldState.snackbarHostState,
        label = "SnackBarTransition"
    )
    val snackBarOffsetAnim by snackBarTransition.animateDp(
        label = "snackBarOffsetAnim",
        transitionSpec = {
            TweenSpec(
                durationMillis = 300,
                easing = LinearEasing,
            )
        }
    ) {
        when (it.currentSnackbarData) {
            null -> {
                100.getDp()
            }
            else -> {
                0.getDp()
            }
        }
    }

    var isOnboardingCompleted by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(user != null) }

    Box {
        val (cartOffset, setCartOffset) = remember { mutableStateOf(IntOffset(0, 0)) }
        ScaffoldSection(
            controller = controller,
            scaffoldState = scaffoldState,
            cartOffset = cartOffset,
            user = user,
            cartItems = cartItems,
            productsOnCartIds = productsOnCartIds,
            productsOnBookmarksIds = productsOnBookmarksIds,
            onStatusBarColorChange = onStatusBarColorChange,
            bottomNavigationContent = {
                if (
                    currentRouteAsState in destinations.map { it.route }
                    || currentRouteAsState == Screen.Cart.route
                ) {
                    AppBottomNav(
                        activeRoute = currentRouteAsState,
                        backgroundColor = MaterialTheme.colors.surface,
                        bottomNavDestinations = destinations,
                        onCartOffsetMeasured = { offset ->
                            setCartOffset(offset)
                        },
                        onActiveRouteChange = {
                            if (it != currentRouteAsState) {
                                controller.navigate(it) {
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            },
            onSplashFinished = { nextDestination ->
                controller.navigate(nextDestination.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            },
            onBoardFinished = {
                isOnboardingCompleted = true
                controller.navigate(Screen.Login.route) {
                    popUpTo(Screen.Onboard.route) {
                        inclusive = true
                    }
                }
            },
            onBackRequested = {
                controller.popBackStack()
            },
            onNavigationRequested = { route, removePreviousRoute ->
                if (removePreviousRoute) {
                    controller.popBackStack()
                }
                controller.navigate(route)
            },
            onShowProductRequest = { productId ->
                controller.navigate(
                    Screen.ProductDetails.route
                        .replace("{productId}", "$productId")
                )
            },
            onUpdateCartRequest = { productId ->
                holderViewModel.updateCart(
                    productId = productId,
                    currentlyOnCart = productId in productsOnCartIds,
                )
            },
            onUpdateBookmarkRequest = { productId ->
                holderViewModel.updateBookmarks(
                    productId = productId,
                    currentlyOnBookmarks = productId in productsOnBookmarksIds,
                )
            },
            onUserNotAuthorized = { removeCurrentRoute ->
                if (removeCurrentRoute) {
                    controller.popBackStack()
                }
                controller.navigate(Screen.Login.route)
            },
            onToastRequested = { message, color ->
                scope.launch {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    setSnackBarColor(color)
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short,
                    )
                }
            },
            isOnboardingCompleted = isOnboardingCompleted,
            isLoggedIn = isLoggedIn,
            setIsLoggedIn = { isLoggedIn = it }
        )

        CustomSnackBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = snackBarOffsetAnim),
            snackHost = scaffoldState.snackbarHostState,
            backgroundColorProvider = { snackBarColor },
        )
    }
}

@Composable
fun ScaffoldSection(
    controller: NavHostController,
    scaffoldState: ScaffoldState,
    cartOffset: IntOffset,
    user: User?,
    cartItems: List<CartItem>,
    productsOnCartIds: List<Int>,
    productsOnBookmarksIds: List<Int>,
    onStatusBarColorChange: (color: Color) -> Unit,
    onSplashFinished: (nextDestination: Screen) -> Unit,
    onBoardFinished: () -> Unit,
    onNavigationRequested: (route: String, removePreviousRoute: Boolean) -> Unit,
    onBackRequested: () -> Unit,
    onUpdateCartRequest: (productId: Int) -> Unit,
    onUpdateBookmarkRequest: (productId: Int) -> Unit,
    onShowProductRequest: (productId: Int) -> Unit,
    onUserNotAuthorized: (removeCurrentRoute: Boolean) -> Unit,
    onToastRequested: (message: String, color: Color) -> Unit,
    bottomNavigationContent: @Composable () -> Unit,
    isOnboardingCompleted: Boolean,
    isLoggedIn: Boolean,
    setIsLoggedIn: (Boolean) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = controller,
                startDestination = when {
                    isLoggedIn -> Screen.Home.route
                    isOnboardingCompleted -> Screen.Login.route
                    else -> Screen.Onboard.route
                }
            ) {
                composable(Screen.Splash.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    SplashScreen(onSplashFinished = onSplashFinished)
                }
                composable(Screen.Onboard.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    OnboardScreen(onBoardFinished = onBoardFinished)
                }
                composable(Screen.Signup.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    SignupScreen()
                }
                composable(Screen.Login.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    LoginScreen(
                        onUserAuthenticated = {
                            setIsLoggedIn(true)
                            controller.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onToastRequested = onToastRequested,
                    )
                }
                composable(Screen.Home.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    HomeScreen(
                        cartOffset = cartOffset,
                        cartProductsIds = productsOnCartIds,
                        bookmarkProductsIds = productsOnBookmarksIds,
                        onProductClicked = onShowProductRequest,
                        onCartStateChanged = onUpdateCartRequest,
                        onBookmarkStateChanged = onUpdateBookmarkRequest,
                    )
                }
                composable(Screen.Notifications.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    NotificationScreen()
                }
                composable(Screen.Search.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    SearchScreen()
                }
                composable(Screen.Bookmark.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    BookmarksScreen(
                        cartOffset = cartOffset,
                        cartProductsIds = productsOnCartIds,
                        onProductClicked = onShowProductRequest,
                        onCartStateChanged = onUpdateCartRequest,
                        onBookmarkStateChanged = onUpdateBookmarkRequest,
                    )
                }
                composable(Screen.Cart.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    CartScreen(
                        user = user,
                        cartItems = cartItems,
                        onProductClicked = onShowProductRequest,
                        onUserNotAuthorized = { onUserNotAuthorized(false) },
                        onCheckoutRequest = {
                            onNavigationRequested(Screen.Checkout.route, false)
                        },
                    )
                }
                composable(Screen.Checkout.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    user.whatIfNotNull(
                        whatIf = {
                            CheckoutScreen(
                                navController = controller,
                                cartItems = cartItems,
                                onBackRequested = onBackRequested,
                                onCheckoutSuccess = {
                                    // This callback is invoked when checkout is successful
                                    // Show the congratulation dialog and navigate based on user choice
                                },
                                onToastRequested = onToastRequested,
                                onChangeLocationRequested = {
                                    onNavigationRequested(Screen.LocationPicker.route, false)
                                }
                            )
                        },
                        whatIfNot = {
                            LaunchedEffect(key1 = Unit) {
                                onUserNotAuthorized(true)
                            }
                        },
                    )
                }
                composable(Screen.LocationPicker.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    LocationPickerScreen(
                        onLocationRequested = {

                        },
                        onLocationPicked = {

                        }
                    )
                }
                composable(Screen.Profile.route) {
                    user.whatIfNotNull(
                        whatIf = {
                            onStatusBarColorChange(MaterialTheme.colors.background)
                            ProfileScreen(
                                user = it,
                                onNavigationRequested = { route, removePreviousRoute ->
                                    if (route == Screen.Login.route) {
                                        setIsLoggedIn(false)
                                    }
                                    onNavigationRequested(route, removePreviousRoute)
                                },
                            )
                        },
                        whatIfNot = {
                            LaunchedEffect(key1 = Unit) {
                                onUserNotAuthorized(false)
                            }
                        },
                    )
                }
                composable(Screen.OrderHistory.route) {
                    user.whatIfNotNull(
                        whatIf = {
                            onStatusBarColorChange(MaterialTheme.colors.background)
                            OrdersHistoryScreen(
                                onBackRequested = onBackRequested,
                            )
                        },
                        whatIfNot = {
                            LaunchedEffect(key1 = Unit) {
                                onUserNotAuthorized(true)
                            }
                        },
                    )
                }
                composable(
                    route = Screen.ProductDetails.route,
                    arguments = listOf(
                        navArgument(name = "productId") { type = NavType.IntType }
                    ),
                ) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    val productId = it.arguments?.getInt("productId")
                        ?: throw IllegalArgumentException("Product id is required")

                    ProductDetailsScreen(
                        productId = productId,
                        cartItemsCount = cartItems.size,
                        isOnCartStateProvider = { productId in productsOnCartIds },
                        isOnBookmarksStateProvider = { productId in productsOnBookmarksIds },
                        onUpdateCartState = onUpdateCartRequest,
                        onUpdateBookmarksState = onUpdateBookmarkRequest,
                        onBackRequested = onBackRequested,
                    )
                }
            }
            bottomNavigationContent()
        }
    }
}

@Composable
fun getActiveRoute(navController: NavHostController): String {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route ?: "splash"
}



