package com.taqsiim.banquemisrloginclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.taqsiim.banquemisrloginclone.ui.theme.BanqueMisrLoginCloneTheme
import com.taqsiim.banquemisrloginclone.ui.theme.brandRed
import com.taqsiim.banquemisrloginclone.ui.theme.lightPink

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BanqueMisrLoginCloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    // Get current language to decide which language to switch to
    val currentLocale = LocalConfiguration.current.locales[0]

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            // ## 1. Top Bar Section ##
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bm_icon),
                    contentDescription = stringResource(id = R.string.bank_logo_description),
                    modifier = Modifier.height(40.dp)
                )
                TextButton(onClick = {
                    // Switch between Arabic and English
                    val targetLanguage = if (currentLocale.language == "ar") "en" else "ar"
                    val appLocale = LocaleListCompat.forLanguageTags(targetLanguage)
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }) {
                    Text(
                        text = stringResource(id = R.string.language_button),
                        color = brandRed,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // ## 2. Login Form Section ##
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(stringResource(id = R.string.username_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(id = R.string.password_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (isPasswordVisible) R.drawable.eye_open else R.drawable.eye_closed
                    val description = if (isPasswordVisible) stringResource(id = R.string.show_password_description)
                    else stringResource(id = R.string.hide_password_description)
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(painter = painterResource(id = image), contentDescription = description)
                    }
                },
                supportingText = {
                    if (password.isNotEmpty() && password.length < 8) {
                        Text(
                            text = stringResource(id = R.string.password_length_error),
                            color = Color.Red
                        )
                    }
                }
            )

            TextButton(onClick = { /* Handle forgot password */ }) {
                Text(
                    text = stringResource(id = R.string.forgot_password_link),
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            val fieldsNotEmpty = username.isNotEmpty() && password.isNotEmpty()
            Button(
                onClick = { /* Handle login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (fieldsNotEmpty) brandRed else lightPink
                ),
                enabled = fieldsNotEmpty
            ) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    color = if (fieldsNotEmpty) Color.White else Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.need_help_prompt),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = stringResource(id = R.string.contact_us_link),
                    fontSize = 14.sp,
                    color = brandRed,
                    fontWeight = FontWeight.Bold,
                )
            }

//            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
            BottomMenu()

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun BottomMenu() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MenuItem(iconResId = R.drawable.our_products, text = stringResource(id = R.string.menu_products))
        MenuItem(iconResId = R.drawable.exchange_rate, text = stringResource(id = R.string.menu_exchange))
        MenuItem(iconResId = R.drawable.security_tips, text = stringResource(id = R.string.menu_security))
        MenuItem(iconResId = R.drawable.nearest_branch_or_atm, text = stringResource(id = R.string.menu_atm))
    }
}

@Composable
fun MenuItem(iconResId: Int, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.width(80.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            lineHeight = 14.sp
        )
    }
}

//@Preview(showBackground = true, locale = "ar")
@Preview(showBackground = true, locale = "en")
@Composable
fun DefaultPreview() {
    BanqueMisrLoginCloneTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LoginScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}
