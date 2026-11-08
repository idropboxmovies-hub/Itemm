package com.loja.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Product(val name: String, val price: String, val emoji: String)

val Blue = Color(0xFF2E86DE)
val categories = listOf("Mac" to "💻", "iPhone" to "📱", "Watch" to "⌚", "AirPods" to "🎧")
val products = listOf(
    Product("iPhone 14", "R$ 6.999", "📱"),
    Product("AirPods 3", "R$ 1.799", "🎧"),
    Product("Apple Watch", "R$ 4.299", "⌚"),
    Product("MacBook Air", "R$ 9.499", "💻"),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var logged by remember { mutableStateOf(false) }
                if (logged) HomeScreen() else LoginScreen { logged = true }
            }
        }
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var id by remember { mutableStateOf("") }
    var remember by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(24.dp).systemBarsPadding(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.weight(1f))
        Text("", fontSize = 64.sp)
        Spacer(Modifier.height(32.dp))
        Text("Entre para um checkout mais rápido.", fontSize = 32.sp, fontWeight = FontWeight.Bold, lineHeight = 38.sp)
        Spacer(Modifier.height(32.dp))
        OutlinedTextField(
            value = id, onValueChange = { id = it }, label = { Text("Apple ID") },
            singleLine = true, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(),
            trailingIcon = { IconButton(onClick = onLogin) { Icon(Icons.Default.ArrowForward, null) } }
        )
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(remember, { remember = it })
            Text("Lembrar de mim", fontSize = 13.sp, color = Color.Gray)
            Spacer(Modifier.weight(1f))
            Text("Esqueceu o Apple ID?", fontSize = 13.sp, color = Blue)
        }
        Spacer(Modifier.weight(1.5f))
        Text("Não tem um Apple ID? Crie agora.", color = Blue, fontSize = 14.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var cart by remember { mutableIntStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Apple Store", fontWeight = FontWeight.SemiBold) },
                actions = {
                    IconButton({}) { Icon(Icons.Default.Search, "Buscar") }
                    BadgedBox({ if (cart > 0) Badge { Text("$cart") } }) {
                        IconButton({}) { Icon(Icons.Default.ShoppingCart, "Carrinho") }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(true, {}, { Icon(Icons.Default.Home, null) }, label = { Text("Loja") })
                NavigationBarItem(false, {}, { Icon(Icons.Default.Place, null) }, label = { Text("Lojas") })
                NavigationBarItem(false, {}, { Icon(Icons.Default.Person, null) }, label = { Text("Perfil") })
            }
        }
    ) { pad ->
        Column(Modifier.padding(pad).verticalScroll(rememberScrollState()).padding(16.dp)) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(categories) { (name, e) ->
                    Column(
                        Modifier.size(84.dp).clip(RoundedCornerShape(14.dp)).background(Color(0xFFF5F5F7)),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                    ) { Text(e, fontSize = 28.sp); Text(name, fontSize = 11.sp) }
                }
            }
            Spacer(Modifier.height(16.dp))
            Box(
                Modifier.fillMaxWidth().height(130.dp).clip(RoundedCornerShape(14.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFF0B0B3B), Color(0xFF3A1C71)))).padding(16.dp)
            ) {
                Column {
                    Text("OFERTA POR TEMPO LIMITADO", color = Color.White, fontSize = 10.sp)
                    Text("Economize 5% em todos os produtos com Apple Card", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Novidades", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Veja o que há de novo agora", fontSize = 13.sp, color = Color.Gray)
                }
                Text("Ver tudo", color = Blue, fontSize = 13.sp)
            }
            Spacer(Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(products) { p ->
                    Card(Modifier.width(160.dp), colors = CardDefaults.cardColors(Color(0xFFF5F5F7))) {
                        Column(Modifier.padding(12.dp)) {
                            Box(Modifier.fillMaxWidth().height(110.dp), contentAlignment = Alignment.Center) { Text(p.emoji, fontSize = 56.sp) }
                            Text(p.name, fontWeight = FontWeight.SemiBold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(p.price, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.weight(1f))
                                Button({ cart++ }, colors = ButtonDefaults.buttonColors(Blue), contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp), modifier = Modifier.height(30.dp)) { Text("Comprar", fontSize = 12.sp) }
                            }
                        }
                    }
                }
            }
        }
    }
}
