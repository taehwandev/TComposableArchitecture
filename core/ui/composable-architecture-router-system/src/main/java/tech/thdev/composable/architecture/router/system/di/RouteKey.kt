package tech.thdev.composable.architecture.router.system.di

import dagger.MapKey
import tech.thdev.composable.architecture.router.system.route.ActivityRoute
import kotlin.reflect.KClass

/**
 * Use with Dagger or Hilt's IntoMap and RouterKey.
 *
 * Write the following code in your Dagger or Hilt module.
 *
 * Define the interface for MainActivity.
 * ```kotlin
 * interface MainActivityRoute : ActivityRoute
 * ```
 *
 * When using Activity, initialize the Intent in the implementation as follows.
 *
 * ```kotlin
 * class MainActivityRouteImpl @Inject internal constructor() : MainActivityRoute {
 *
 *  override fun getActivity(context: Context): Intent =
 *      Intent(context, MainActivity::class.java)
 * }
 * ```
 *
 * And define the Dagger / Hilt module as follows.
 *
 * ```kotlin
 * @Module
 * @InstallIn(SingletonComponent::class)
 * abstract class MainModule {
 *
 *  @Binds
 *  @IntoMap
 *  @RouteKey(MainActivityRoute::class)
 *  abstract fun bindMainActivityRoute(
 *      mainActivityRoute: MainActivityRouteImpl,
 *  ): ActivityRoute
 * }
 * ```
 */
@MapKey
annotation class RouteKey(
    val value: KClass<out ActivityRoute>,
)
