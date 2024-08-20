package com.example.blogmultiplatform.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.blogmultiplatform.models.Theme
import com.example.blogmultiplatform.models.User
import com.example.blogmultiplatform.models.UserWithoutPassword
import com.example.blogmultiplatform.navigation.Screen
import com.example.blogmultiplatform.styles.LoginInputStyle
import com.example.blogmultiplatform.util.Constants.FONT_FAMILY
import com.example.blogmultiplatform.util.Id
import com.example.blogmultiplatform.util.Res
import com.example.blogmultiplatform.util.checkUserExistence
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.ButtonSize
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.forms.InputGroup
import com.varabyte.kobweb.silk.components.forms.TextInput
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.set

@Page
@Composable
fun LoginScreen() {
    var errorText by remember { mutableStateOf(" ") }
    val scope = rememberCoroutineScope()
    val context = rememberPageContext()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(leftRight = 50.px, top = 50.px, bottom = 24.px)
                .backgroundColor(Theme.LightGray.rgb),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.margin(bottom = 50.px).width(100.px),
                src = Res.Image.logo,
                description = "Logo Image"
            )
            var username by remember { mutableStateOf("") }
            Input(
                type = InputType.Text,
                modifier = LoginInputStyle.toModifier().attrsModifier {
                    style {
                        width(350.px)
                        height(54.px)
                        backgroundColor(Colors.White)
                    }
                }
                    .id(Id.usernameInput),
                value = username,
                onValueChanged = { username = it },
                placeholder = "Username"
            )
            Spacer()
            var showPassword by remember { mutableStateOf(false) }
            var password by remember { mutableStateOf("") }
            InputGroup(LoginInputStyle.toModifier().attrsModifier {
                style {
                    width(350.px)
                    height(54.px)
                }
            }.margin(top = 8.px)) {
                TextInput(
                    password,
                    password = !showPassword,
                    onTextChanged = { password = it },
                    modifier = Modifier.attrsModifier {
                        style {
                            width(350.px)
                            height(54.px)
                            backgroundColor(Colors.White)
                        }
                    }
                        .id(Id.passwordInput),
                    placeholder = "Password"
                )
                RightInset(width = 4.5.cssRem, modifier = Modifier.padding(16.px)) {
                    Button(
                        onClick = { showPassword = !showPassword },
                        Modifier.width(3.5.cssRem).height(1.75.cssRem),
                        size = ButtonSize.SM,
                    ) {
                        Text(if (showPassword) "Hide" else "Show")
                    }
                }
            }

            Button(
                onClick = {
                    scope.launch {
                        val usernameInput = (document.getElementById(Id.usernameInput) as HTMLInputElement).value
                        val passwordInput = (document.getElementById(Id.passwordInput) as HTMLInputElement).value
                        if (usernameInput.isNotEmpty() && passwordInput.isNotEmpty()){
                            val user = checkUserExistence(
                                user = User(
                                    username = usernameInput,
                                    password = passwordInput
                                )
                            )
                            if (user != null){
                                rememberLoggedIn(
                                    remember = true,
                                    user = user
                                )
                                context.router.navigateTo(Screen.AdminHome.route)
                            }else{
                                errorText = "user doesn't exist."
                                delay(3000)
                                errorText = " "
                            }
                        }else{
                            errorText = "Input fields are empty."
                            delay(3000)
                            errorText = " "
                        }
                    }

                },
                modifier = Modifier.margin(top = 16.px).attrsModifier {
                    style {
                        backgroundColor(Theme.Primary.rgb)
                        width(350.px)
                        height(54.px)
                        color(Colors.White)
                        fontFamily(FONT_FAMILY)
                    }
                }.fontWeight(FontWeight.Medium)
                    .cursor(Cursor.Pointer),
                size = ButtonSize.LG
            ) {
                SpanText(text = "Sign in")
            }

            SpanText(
                text = errorText,
                modifier = Modifier.width(350.px)
                    .color(Colors.Red)
                    .textAlign(TextAlign.Center)
                    .fontFamily(FONT_FAMILY)
                    .fontWeight(FontWeight.Medium)
            )

        }
    }
}

private fun rememberLoggedIn(
    remember: Boolean,
    user: UserWithoutPassword? = null
){
    localStorage["remember"] = remember.toString()
    if (user != null){
        localStorage["userId"] = user._id
        localStorage["username"] = user.username
    }
}