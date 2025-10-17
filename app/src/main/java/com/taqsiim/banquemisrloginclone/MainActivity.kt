package com.taqsiim.banquemisrloginclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.taqsiim.banquemisrloginclone.ui.theme.BanqueMisrLoginCloneTheme

class MainActivity : ComponentActivity() {
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
    // State for the input fields
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    // Define colors for easy reuse
    val brandRed = Color(0xFFC62828)
    val lightPink = Color(0xFFF8E8E8)

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            // Spacer to push content down from the status bar
            Spacer(modifier = Modifier.height(64.dp))

            // ## 1. Top Bar Section ##
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bm_icon), // Your logo here
                    contentDescription = "Bank Logo",
                    modifier = Modifier.height(40.dp)
                )
                TextButton(onClick = { /* Handle language change */ }) {
                    Text(
                        text = "العربية", // This should be from strings.xml
                        color = brandRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // ## 2. Login Form Section ##
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }, // Also from strings.xml
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") }, // Also from strings.xml
                modifier = Modifier.fillMaxWidth(),
                singleLine = true

            )
//
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Password") }, // Also from strings.xml
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                trailingIcon = {
//                    val image = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                    val description = if (isPasswordVisible) "Hide password" else "Show password"
//                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
//                        Icon(imageVector = image, contentDescription = description)
//                    }
//                }
//            )

            TextButton(
                onClick = { /* Handle forgot password */ },
//                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = "Forgot username/password?", // from strings.xml
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Handle login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = lightPink)
            ) {
                Text(
                    text = "Login", // from strings.xml
                    color = brandRed,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // "Need help? Contact us" text
            val annotatedString = buildAnnotatedString {
                append("Need help? ") // from strings.xml
                pushStringAnnotation(tag = "CONTACT_US", annotation = "contact_us_url")
                withStyle(style = SpanStyle(color = brandRed, fontWeight = FontWeight.Bold)) {
                    append("Contact us") // from strings.xml
                }
                pop()
            }

            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "CONTACT_US", start = offset, end = offset)
                        .firstOrNull()?.let {
                            // Handle click on "Contact us"
                        }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Fills remaining space to push the bottom menu down
            Spacer(modifier = Modifier.weight(1f))

            // ## 3. Bottom Menu Section ##
            BottomMenu()

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

//fun Modifier.Companion.align(start: Alignment.Horizontal): Modifier {
//    TODO("Not yet implemented")
//}


@Composable
fun BottomMenu() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MenuItem(iconResId = R.drawable.our_products, text = "Our products")
        MenuItem(iconResId = R.drawable.exchange_rate, text = "Exchange rate")
        MenuItem(iconResId = R.drawable.security_tips, text = "Security tips")
        MenuItem(iconResId = R.drawable.nearest_branch_or_atm, text = "Nearest branch or ATM")
    }
}

@Composable
fun MenuItem(iconResId: Int, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.width(80.dp) // Give each item a fixed width for alignment
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text, // All these texts should be string resources
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            lineHeight = 14.sp // Helps with text wrapping
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BanqueMisrLoginCloneTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            LoginScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}
