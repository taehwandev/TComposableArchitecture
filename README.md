# TComposableArchitecture

T-ComposableArchitecture is a sample project that explores a structure similar to Reducers, UiState, and SideEffects from React.

This architecture is inspired by the [swift-composable-architecture](https://github.com/pointfreeco/swift-composable-architecture) (TCA).

It's intended for educational and sample usage, built using Compose and Hilt.

This code primarily consists of:

- action-system : This manages actions sent from Compose, received and processed by the ViewModel (using a reducer pattern). It also facilitates sending new actions when needed.
- sample app : The sample app demonstrates how to use the architecture.

# Download

Use gradle - compose 1.7.6, compose compiler k2, kotlin 2.0.21

```
implementation("tech.thdev:composable-architecture-system:25.2.0")
```

Release version are available in [Sonatyp's repository.](https://search.maven.org/search?q=tech.thdev)

# CaViewModel and CaActivity

[Sample code](https://github.com/taehwandev/TComposableArchitecture/blob/main/app/src/main/java/tech/thdev/composable/architecture/app/feature/main/MainActivity.kt)

While usage location within your `@Composables` doesn't matter, two base classes are provided for convenience:

- CaViewModel : This class handles events received from the action-system, implementing a reducer and enabling SideEffect usage for one-time events.
- CaActivity : This base class simplifies the integration of the action-system. You can implement your `ContentView()` by inheriting from it.

Implement your Actions:

```kotlin
sealed interface Action : CaAction {

    data object Task : Action // Use object for simple actions
    
    data object LoadData : Action // Use object for simple actions
}
```

Implement your SideEffects:

```kotlin
sealed interface SideEffect : CaSideEffect {

    data object ShowToast : SideEffect // Use object for simple side effects
}
```

CaViewModel is designed to work with [tech.thdev.composable.architecture.action.system.CaActionSender].
It uses a structure similar to a reducer, allowing you to utilize it as shown below. It also supports SideEffect handling.

```kotlin
@HiltViewModel
class MainViewModel @Inject constructor(
    flowCaActionStream: FlowCaActionStream,
) : CaViewModel<Action, SideEffect>(flowCaActionStream, Action::class) {

    private val _uiState = MutableStateFlow(UiState())
    val state = _uiState.asStateFlow()

    override suspend fun reducer(action: Action): CaAction =
        when (action) {
            is Action.Task -> {
                _uiState.value = UiState(showPlaceholder = true)
                Action.LoadData // next event
            }

            is Action.Send -> {
                val loadEnd = // load network
                _uiState.value = UiState(text = loadEnd)

                sendSideEffect(SideEffect.ShowToast)

                CaActionNone // Or return another action
            }
            // ... other actions
        }
}
```

After inheriting from CaViewModel, you must handle two values in the base class:
- Required: Call `viewModel.loadAction()`
- Optional: Collect `viewModel.sideEffect` using `viewModel.sideEffect.collectAsEvent { ... }`

[tech.thdev.composable.architecture.util.collectAsEvent] is a pre-defined function that collects side effects with a default lifecycle state of [Lifecycle.State.STARTED].

```kotlin
@AndroidEntryPoint
class MainActivity : CaActionActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @Composable
    override fun ContentView() {
        TComposableArchitectureTheme {
            val action = LocalActionOwner.current

            Column {
              Button(
                onClick = action.send(Action.LoadData),
              ) {
                Text(
                  text = "OnClick",
                )
              }
            
              Text(
                text = uiState.text,
              )
            }

            LaunchedEffect(Unit) {
                mainViewModel.loadAction() // Required: Load actions
                mainViewModel.action(Action.Task) // Option task
            }

            mainViewModel.sideEffect.collectAsEvent { // Optional: Handle side effects
                when (it) {
                    SideEffect.ShowToast -> {
                        Toast.makeText(this@MainActivity, "message", Toast.LENGTH_SHORT).show()
                    }
                    // ... other side effects
                }
            }
        }
    }
}
```

Or composable function

```kotlin
@Composable
fun SomeScreen(
    mainViewModel: MainViewModel = viewModels(),
) {
    val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

    SomeScreen(
        uiState = uiState,
    )

    LaunchEffect(Unit) {
        mainViewModel.loadAction() // Required: Load actions
        mainViewModel.action(Action.Task) // Option task
    }
}

@Composable
fun SomeScreen(
    uiState: UiState,
) {
    val action = LocalActionOwner.current

    Column {
        Button(
            onClick = action.send(Action.LoadData),
        ) {
            Text(
                text = "OnClick",
            )
        }
    
        Text(
            text = uiState.text,
        )
    }
}
```

# License

This library is released under the MIT license. See [LICENSE](LICENSE) for details.
