package com.example.codelab

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codelab.ui.CodeLabTheme
import android. content. res. Configuration. UI_MODE_NIGHT_YES
import androidx.compose.ui.graphics.toArgb

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        window.apply {
            window.statusBarColor = Color(0xFF795548).toArgb()
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        setContent {
            CodeLabTheme() {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            CourseList(courses)
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome to the Academic Courses App!",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                modifier = Modifier.padding(vertical = 16.dp),
                onClick = onContinueClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Continue", fontSize = 16.sp)
            }
        }
    }
}

// Define a lighter blue color for consistency with the status bar and cards

data class Course(
    val title: String,
    val code: String,
    val creditHours: Int,
    val description: String,
    val prerequisites: String
)

val courses = listOf(
    Course("Mobile Application Development", "SECT-3113", 3, "Teaches how to design and develop mobile apps for Android or iOS. Focuses on UI/UX, app lifecycle, and backend integration.", "Web Design and Programming"),
    Course("Computer Graphics", "SECT-3132", 3, "This course covers techniques for generating and manipulating visual content.You'll learn about rendering, modeling, and animation using graphics algorithms.", "Fundamentals of Electrical Circuit and Electronics"),
    Course("Fundamentals of AI", "SECT-3151", 2, " Introduces core AI concepts like search algorithms, machine learning, and reasoning.Focuses on how machines can mimic intelligent human behavior.", "Fundamentals of Software Engineering"),
    Course("Fundamentals of Cybersecurity", "SECT-3141", 2, "Covers basic principles for protecting systems, networks, and data.Includes cryptography, threat detection, and secure system design.", "None"),
    Course("Operating Systems and System Programming", "SECT-3082", 3, " Explains how operating systems manage hardware and software resources.Includes process control, memory management, and low-level programming.", "Computer Organization and Architecture")
)

@Composable
fun CourseList(courseList: List<Course>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = courseList) { course ->
            CourseCard(course)
        }
    }
}

@Composable
fun CourseCard(course: Course, modifier: Modifier = Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface),
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${course.title} (${course.code})",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = "Credit Hours: ${course.creditHours}", color = MaterialTheme.colorScheme.onSurface)

                    if (expanded) {
                        Text(text = "Description: ${course.description}", color = MaterialTheme.colorScheme.onSurface)
                        Text(text = "Prerequisites: ${course.prerequisites}", color = MaterialTheme.colorScheme.secondary)
                    }
                }

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                        contentDescription = if (expanded) "Show Less" else "Show More",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_YES, name = "CoursePreviewDark")
@Preview(showBackground = true, widthDp = 320)
@Composable
fun CoursePreview() {
    CodeLabTheme {
        CourseList(courseList = courses)
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    CodeLabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview
@Composable
fun MyAppPreview() {
    CodeLabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}