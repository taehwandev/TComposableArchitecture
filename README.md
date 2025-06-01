# TComposableArchitecture

T-ComposableArchitecture is a sample project that explores a structure similar to Reducers, UiState, and SideEffects from React.

This architecture is inspired by the swift-composable-architecture (TCA).

It's intended for educational and sample usage, built using Compose and Hilt, Compose navigation.

This code primarily consists of:

- action-system: This manages actions sent from Compose, received and processed by the ViewModel (using a reducer pattern). It also facilitates sending new actions when needed.
- router-system: Enables operations using Activity/Navigation through the router system.
- sample app: The sample app demonstrates how to use the architecture.


# Blog
- [T Composable Architecture for Compose (React-inspired) - en](https://medium.com/@taehwandev/t-composable-architecture-for-compose-react-inspired-6ef28d799100)
- [컴포즈에 사용할 Composable Architecutre 설명(리엑트?) - ko](https://thdev.tech/architecture/2025/02/02/Android-Architecture-01/)

# Download

Use gradle - compose bom 2025.05.01, kotlin 2.1.21

```
implementation("tech.thdev:composable-architecture-system:25.2.0")
```

Release version are available in [Sonatyp's repository.](https://search.maven.org/search?q=tech.thdev)

# ActionViewModel

```kotlin
// UiState sample
@Immutable
internal data class SettingsUiState(
    val mode: Mode,
) {

    enum class Mode {
        LIGHT,
        DARK,
        AUTO,
    }

    companion object {

        val Default = SettingsUiState(
            mode = Mode.AUTO,
        )
    }
}

// Action definition
internal sealed interface SettingsAction : Action {

    data class ThemeChange(val newType: SettingsUiState.Mode) : SettingsAction
}
```

Inherit and implement ActionViewModel

```
// ViewModel
@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    flowActionStream: FlowActionStream,
) : ActionViewModel<SettingsAction>(flowActionStream, SettingsAction::class) {

    private val _settingsUiState = MutableStateFlow(SettingsUiState.Default)
    val settingsUiState = _settingsUiState.asStateFlow()

    override suspend fun handleAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.ThemeChange -> { // Changed to 'is' for type checking sealed interface
                // Your action
                _settingsUiState.update {
                    it.copy(
                        mode = action.newType,
                    )
                }
            }
        }
    }
}
```

Test writing example

```kotlin
internal class ActionViewModelTest {

    private val flowActionStream = mock<FlowActionStream>()

    private val viewModel = SettingsViewModel(flowActionStream)

    @Test
    fun `test ThemeModeChange`() = runTest {
        // Corrected action name based on definition: ThemeChange, not ThemeModeChange
        val mockItem = SettingsAction.ThemeChange(newType = SettingsUiState.Mode.DARK)
        whenever(flowActionStream.flowAction()).thenReturn(flowOf(mockItem))

        viewModel.flowAction // Assuming viewModel exposes flowAction for testing, or this needs adjustment based on actual API
            .test {
                Assert.assertEquals(
                    // Corrected action name
                    SettingsAction.ThemeChange(newType = SettingsUiState.Mode.DARK),
                    awaitItem()
                )

                Assert.assertEquals(
                    SettingsUiState(
                        mode = SettingsUiState.Mode.DARK,
                    ),
                    viewModel.settingsUiState.value
                )

                verify(flowActionStream).flowAction()

                cancelAndIgnoreRemainingEvents()
            }
    }
}
```

(Self-correction in the test code: SettingsAction.ThemeModeChange was used in the original test example, but the SettingsAction definition shows ThemeChange. I've corrected it to ThemeChange and action.newType for consistency. Also, the when (action) in handleAction should use is SettingsAction.ThemeChange for smart casting with sealed interfaces.)

# Using with Composable

When using with Compose, if you want to use Actions, you must call `ActionSenderCompositionLocalProvider`.

If you want to use it without Actions, you must call `LaunchedLifecycleActionViewModel(viewModel = actionViewModel)`.

```kotlin
@Composable
internal fun InternalSettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    ActionSenderCompositionLocalProvider(settingsViewModel) {
        val settingsUiState by settingsViewModel.settingsUiState.collectAsStateWithLifecycle()

        InternalSettingsScreen(
            onThemeModeSelectBox = { modifier ->
                ThemeModeSelectBox(
                    mode = settingsUiState.mode,
                    // Assuming ThemeModeSelectBox has an onClick to send an action
                    onClick = { newMode ->
                        // Example of sending an action, adjust as per actual ThemeModeSelectBox
                        settingsViewModel.dispatch(SettingsAction.ThemeChange(newType = newMode))
                    },
                    modifier = modifier
                )
            },
        )
    }
}

@Composable
private fun InternalSettingsScreen(
    onThemeModeSelectBox: @Composable (modifier: Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        onThemeModeSelectBox(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInternalSettingsScreen() {
    var uiState by remember { mutableStateOf(SettingsUiState.Default) }
    InternalSettingsScreen(
        onThemeModeSelectBox = { modifier ->
            ThemeModeSelectBox(
                mode = uiState.mode,
                onClick = { mode ->
                    uiState = SettingsUiState(mode = mode)
                },
                modifier = modifier
            )
        },
        modifier = Modifier
            .fillMaxSize()
    )
}
```

# License

This library is released under the MIT license. See [LICENSE](LICENSE) for details.
