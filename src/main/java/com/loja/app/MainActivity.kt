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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val Bg = Color(0xFFFAFAFA)
private val Ink = Color(0xFF1D1D1F)
private val Gray = Color(0xFF8E8E93)
private val Blue = Color(0xFF2D8CDB)
private val Tile = Color(0xFFF4F4F6)

private fun icon(d: String, filled: Boolean = false): ImageVector =
    ImageVector.Builder(defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = 24f, viewportHeight = 24f)
        .addPath(
            pathData = PathParser().parsePathString(d).toNodes(),
            fill = if (filled) SolidColor(Color.Black) else null,
            stroke = if (filled) null else SolidColor(Color.Black),
            strokeLineWidth = 1.5f, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round
        ).build()

private val IcSearch = icon("M11,4a7,7 0 1,0 0.001,0z M20.5,20.5l-4.5,-4.5")
private val IcBag = icon("M6,8h12l1,12H5z M9,8V7a3,3 0 0,1 6,0v1")
private val IcNearby = icon("M3,11l18,-8l-8,18l-2,-8z")
private val IcOrders = icon("M12,3l8,4.5v9L12,21l-8,-4.5v-9z M4,7.5l8,4.5l8,-4.5 M12,12v9")
private val IcProfile = icon("M12,3a4.5,4.5 0 1,0 0.001,0z M5,21v-1a5,5 0 0,1 5,-5h4a5,5 0 0,1 5,5v1")
private val IcApple = icon("M12.152,6.896c-0.948,0 -2.415,-1.078 -3.96,-1.04 -2.04,0.027 -3.91,1.183 -4.961,3.014 -2.117,3.675 -0.546,9.103 1.519,12.09 1.013,1.454 2.208,3.09 3.792,3.039 1.52,-0.065 2.09,-0.987 3.935,-0.987 1.831,0 2.35,0.987 3.96,0.948 1.637,-0.026 2.676,-1.48 3.676,-2.948 1.156,-1.688 1.636,-3.325 1.662,-3.415 -0.039,-0.013 -3.182,-1.221 -3.22,-4.857 -0.026,-3.04 2.48,-4.494 2.597,-4.559 -1.429,-2.09 -3.623,-2.324 -4.39,-2.376 -2,-0.156 -3.675,1.09 -4.61,1.09z M15.53,3.83c0.843,-1.012 1.4,-2.427 1.245,-3.83 -1.207,0.052 -2.662,0.805 -3.532,1.818 -0.78,0.896 -1.454,2.338 -1.273,3.714 1.338,0.104 2.715,-0.688 3.559,-1.701", true)

data class Item(val name: String, val price: String, val emoji: String)

private val categories = listOf("Mac" to "💻", "iPhone" to "📱", "Watch" to "⌚", "AirPods" to "🎧")
private val latest = listOf(Item("iPhone 14", "$999", "📱"), Item("Airpods 3", "$799", "🎧"))
private val trending = listOf(Item("MacBook Air", "$1199", "💻"), Item("Apple Watch", "$399", "⌚"))

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = 0xFFFAFAFA.toInt()
        window.navigationBarColor = 0xFFFFFFFF.toInt()
        WindowCompat.getInsetsController(window, window.decorView).run {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
        setContent { MaterialTheme { Store() } }
    }
}

@Composable
fun Store() {
    Column(Modifier.fillMaxSize().background(Bg)) {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(IcApple, null, Modifier.size(20.dp), tint = Ink)
                Spacer(Modifier.width(6.dp))
                Text("Apple Store", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Ink, modifier = Modifier.weight(1f))
                Icon(IcSearch, "Search", Modifier.size(22.dp), tint = Ink)
                Spacer(Modifier.width(22.dp))
                Icon(IcBag, "Bag", Modifier.size(22.dp), tint = Ink)
            }
            LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(categories) { (name, e) ->
                    Column(
                        Modifier.size(width = 88.dp, height = 84.dp)
                            .shadow(3.dp, RoundedCornerShape(12.dp), ambientColor = Color(0x14000000), spotColor = Color(0x14000000))
                            .background(Color.White, RoundedCornerShape(12.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                    ) { Text(e, fontSize = 30.sp); Spacer(Modifier.height(4.dp)); Text(name, fontSize = 10.sp, color = Gray) }
                }
            }
            Spacer(Modifier.height(18.dp))
            Box(
                Modifier.padding(horizontal = 20.dp).fillMaxWidth().height(130.dp).clip(RoundedCornerShape(12.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFF0A0A3C), Color(0xFF1B0A55), Color(0xFF2A1068))))
            ) {
                Column(Modifier.padding(start = 20.dp, top = 22.dp)) {
                    Text("LIMITED-TIME OFFER", fontSize = 8.sp, color = Color.White)
                    Spacer(Modifier.height(4.dp))
                    Text("Save 5% on all products\nusing Apple Card", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White, lineHeight = 17.sp)
                }
                Row(Modifier.align(Alignment.BottomEnd).padding(end = 16.dp, bottom = 8.dp).alpha(0.9f)) {
                    Text("📱", fontSize = 40.sp); Text("💳", fontSize = 34.sp); Text("⌚", fontSize = 40.sp)
                }
            }
            Spacer(Modifier.height(24.dp))
            Section("The latest.", "Take a look at what’s new, right now", true)
            ProductRow(latest)
            Spacer(Modifier.height(20.dp))
            Section("Trending Products", null, false)
            ProductRow(trending)
            Spacer(Modifier.height(16.dp))
        }
        Row(
            Modifier.fillMaxWidth().background(Color.White).padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Tab(IcNearby, "Nearby Store"); Tab(IcOrders, "Orders"); Tab(IcProfile, "Profile")
        }
    }
}

@Composable
fun Section(title: String, sub: String?, seeAll: Boolean) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp), verticalAlignment = Alignment.Bottom) {
        Column(Modifier.weight(1f)) {
            Text(title, fontSize = 24.sp, fontWeight = FontWeight.Medium, color = Ink, letterSpacing = (-0.5).sp)
            if (sub != null) Text(sub, fontSize = 12.sp, color = Gray)
        }
        if (seeAll) Text("See All", fontSize = 12.sp, color = Blue)
    }
    Spacer(Modifier.height(14.dp))
}

@Composable
fun ProductRow(list: List<Item>) {
    Row(Modifier.padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        list.forEach { p ->
            Column(
                Modifier.weight(1f)
                    .shadow(3.dp, RoundedCornerShape(10.dp), ambientColor = Color(0x14000000), spotColor = Color(0x14000000))
                    .background(Color.White, RoundedCornerShape(10.dp)).padding(8.dp)
            ) {
                Box(Modifier.fillMaxWidth().height(150.dp).background(Tile, RoundedCornerShape(6.dp)), contentAlignment = Alignment.Center) {
                    Text(p.emoji, fontSize = 72.sp)
                    if (p.name.startsWith("iPhone")) Column(Modifier.align(Alignment.TopEnd).padding(8.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        listOf(Color(0xFF3A3A3C), Color(0xFFE5484D), Color(0xFFD9D6FF), Color(0xFFE5E5EA)).forEach {
                            Box(Modifier.size(5.dp).background(it, CircleShape))
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Column(Modifier.weight(1f)) {
                        Text(p.name, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Ink)
                        Text(p.price, fontSize = 10.sp, color = Gray)
                    }
                    Box(Modifier.clip(RoundedCornerShape(50)).background(Blue).padding(horizontal = 14.dp, vertical = 5.dp)) {
                        Text("Buy", fontSize = 11.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun Tab(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, label, Modifier.size(22.dp), tint = Gray)
        Spacer(Modifier.height(4.dp))
        Text(label, fontSize = 8.sp, color = Gray)
    }
}
