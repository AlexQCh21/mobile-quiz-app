package com.example.s04quezada

import android.media.AudioAttributes
import android.os.Bundle
import android.widget.Chronometer
import android.media.MediaPlayer
import android.media.SoundPool
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    lateinit var cronometro: CronometroInterno

    val preguntas = listOf(
        // 1. Múltiple opción
        Pregunta(
            "¿Cuál de los siguientes archivos define los colores usados en la app?",
            listOf("A) res/layout/colors.xml", "B) AndroidManifest.xml", "C) res/values/colors.xml", "D) build.gradle"),
            "C) res/values/colors.xml"
        ),
        // 2. Múltiple opción
        Pregunta(
            "¿Qué carpeta contiene los archivos XML que definen la interfaz de usuario?",
            listOf("A) res/xml", "B) res/layout", "C) res/drawable", "D) res/values"),
            "B) res/layout"
        ),
        // 3. Múltiple opción
        Pregunta(
            "¿Qué archivo contiene la configuración del nombre del paquete, permisos y componentes?",
            listOf("A) build.gradle", "B) AndroidManifest.xml", "C) res/values/strings.xml", "D) MainActivity.kt"),
            "B) AndroidManifest.xml"
        ),
        // 4. Múltiple opción
        Pregunta(
            "¿Cuál es el propósito principal del archivo `build.gradle`?",
            listOf("A) Configurar los colores de la app", "B) Declarar la interfaz de usuario", "C) Gestionar dependencias y configuración del proyecto", "D) Almacenar datos locales"),
            "C) Gestionar dependencias y configuración del proyecto"
        ),
        // 5. Múltiple opción
        Pregunta(
            "¿Qué componente es ideal para listas grandes con mejor rendimiento que ListView?",
            listOf("A) GridView", "B) ListView", "C) RecyclerView", "D) ScrollView"),
            "C) RecyclerView"
        ),
        // 6. Múltiple opción
        Pregunta(
            "¿Qué componente de arquitectura se usa para manejar lógica de UI separada de la vista?",
            listOf("A) LiveData", "B) ViewModel", "C) Fragment", "D) Service"),
            "B) ViewModel"
        ),
        // 7. Múltiple opción
        Pregunta(
            "¿Qué lenguaje NO es oficialmente soportado por Android Studio?",
            listOf("A) Kotlin", "B) Java", "C) C++", "D) Swift"),
            "D) Swift"
        ),
        // 8. Múltiple opción
        Pregunta(
            "¿Cuál es el propósito de la carpeta `res/values`?",
            listOf("A) Guardar imágenes", "B) Definir layouts", "C) Almacenar recursos como strings y colores", "D) Contener clases Java/Kotlin"),
            "C) Almacenar recursos como strings y colores"
        ),
        // 9. Múltiple opción
        Pregunta(
            "¿Cuál es la extensión de los archivos de diseño en Android Studio?",
            listOf("A) .html", "B) .xml", "C) .json", "D) .kt"),
            "B) .xml"
        ),
        // 10. Múltiple opción
        Pregunta(
            "¿Qué palabra clave se usa en Kotlin para declarar funciones?",
            listOf("A) def", "B) func", "C) fun", "D) function"),
            "C) fun"
        ),
        // 11. Múltiple opción
        Pregunta(
            "¿Qué método se utiliza para iniciar una nueva Activity?",
            listOf("A) launchActivity()", "B) goToActivity()", "C) openActivity()", "D) startActivity()"),
            "D) startActivity()"
        ),
        // 12. Múltiple opción
        Pregunta(
            "¿Cuál es el IDE oficial recomendado por Google para el desarrollo Android?",
            listOf("A) Eclipse", "B) IntelliJ IDEA", "C) Android Studio", "D) Visual Studio Code"),
            "C) Android Studio"
        ),

        // 13. Verdadero/Falso
        Pregunta("¿Android Studio se basa en IntelliJ IDEA?", listOf("Verdadero", "Falso"), "Verdadero"),
        // 14. Verdadero/Falso
        Pregunta("¿Kotlin es totalmente compatible con Java?", listOf("Verdadero", "Falso"), "Verdadero"),
        // 15. Verdadero/Falso
        Pregunta("¿En Kotlin, las variables deben ser declaradas con `var` para ser mutables?", listOf("Verdadero", "Falso"), "Verdadero"),
        // 16. Verdadero/Falso
        Pregunta("¿El operador `!!` en Kotlin fuerza una variable potencialmente nula a no serlo?", listOf("Verdadero", "Falso"), "Verdadero"),
        // 17. Verdadero/Falso
        Pregunta("¿Android Studio puede compilar directamente aplicaciones iOS?", listOf("Verdadero", "Falso"), "Falso"),
        // 18. Verdadero/Falso
        Pregunta("¿`val` en Kotlin se usa para declarar variables mutables?", listOf("Verdadero", "Falso"), "Falso"),
        // 19. Verdadero/Falso
        Pregunta("¿`onCreate()` es parte del ciclo de vida de una `Activity`?", listOf("Verdadero", "Falso"), "Verdadero"),
        // 20. Verdadero/Falso
        Pregunta("¿`res/drawable` es donde se almacenan imágenes como íconos o fondos?", listOf("Verdadero", "Falso"), "Verdadero")
    )


    var cont:Int = 0

    var respuestasIncorrectas = 0
    var respuestasCorrectas = 0
    lateinit var nuevoFragmentPreguntaw:JuegoPreguntaFragment
    lateinit var mediaPlayer:MediaPlayer
    private lateinit var soundPool: SoundPool
    private var sonidoCorrecto = 0
    private var sonidoIncorrecto = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()

        sonidoCorrecto = soundPool.load(this, R.raw.sonido_correcto, 1)
        sonidoIncorrecto = soundPool.load(this, R.raw.sonido_incorrecto, 1)

        mediaPlayer = MediaPlayer.create(this, R.raw.cronometrosound)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        cronometro = CronometroInterno()
        cronometro.iniciar()
        mostrarPregunta()


    }

    fun reproducirCorrecto() {
        soundPool.play(sonidoCorrecto, 1f, 1f, 0, 0, 1f)
    }

    fun reproducirIncorrecto() {
        soundPool.play(sonidoIncorrecto, 1f, 1f, 0, 0, 1f)
    }

    fun volverAComenzar(){
        cont = 0
        respuestasIncorrectas = 0
        respuestasCorrectas = 0
        cronometro.reiniciar()
        cronometro.iniciar()
        mostrarPregunta()

        // Reinicia y vuelve a reproducir el sonido
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    @Suppress("MissingSuperCall")
    override fun onBackPressed() {
        // No hacer nada
    }


    fun mostrarPregunta() {
        if(cont<preguntas.size){
            val preguntaActual = preguntas[cont]
            val fragment = JuegoPreguntaFragment.newInstance(preguntaActual.pregunta, preguntaActual.opciones.joinToString("||"))

            nuevoFragmentPreguntaw = fragment

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit()
        }else{
            cronometro.pausar()
            mostrarResultado()
        }
    }

    fun calcularPuntaje(): String {
        val tiempoMilisegundos = cronometro.obtenerTiempoTranscurrido()
        val tiempoSegundos = tiempoMilisegundos / 1000.0

        if (tiempoSegundos <= 0.0) return "0.00"

        val puntosBase = respuestasCorrectas * 10.0
        val puntaje = puntosBase / (tiempoSegundos/4)

        val puntajeFinal = puntaje.coerceAtLeast(0.0)

        return String.format("%.2f", puntajeFinal)
    }


    fun obtenerTiempoMinutosSegundos(): String {
        val tiempoMilisegundos = cronometro.obtenerTiempoTranscurrido()
        val totalSegundos = (tiempoMilisegundos / 1000).toInt()

        val minutos = totalSegundos / 60
        val segundos = totalSegundos % 60

        return String.format("%d:%02d", minutos, segundos)
    }



    fun mostrarResultado(){
        val fragment = ResultadoFragment.newInstance(calcularPuntaje(), obtenerTiempoMinutosSegundos())

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
        mediaPlayer.stop()
    }


    fun mostrarError() {
        val respuestaCorrecta = preguntas[cont].respuestaCorrecta
        val nuevoFragment = ErrorFragment.newInstance(respuestaCorrecta)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, nuevoFragment)
            .addToBackStack(null)
            .commit()
    }

    fun mostrarBuenaRespuesta() {
        val nuevoFragment = CorrectoFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, nuevoFragment)
            .addToBackStack(null)
            .commit()
    }


    private fun iniciarQuiz() {
        mostrarPregunta()
        cronometro.iniciar()
        val opciones = preguntas[cont].opciones
        cont++
        nuevoFragmentPreguntaw.mostrarPregunta(preguntas[cont].pregunta)

        if(opciones.size>2){
            nuevoFragmentPreguntaw.agregarRadioButtons(true, opciones)
        }
    }

    fun calificarRespuesta(respuestaSeleccionada: String?) {
        if (respuestaSeleccionada == null) {
            Toast.makeText(this, "Selecciona una respuesta", Toast.LENGTH_SHORT).show()
            return
        }

        val preguntaActual = preguntas[cont]
        val respuestaCorrecta = preguntaActual.respuestaCorrecta

        if (respuestaSeleccionada == respuestaCorrecta) {
            respuestasCorrectas++
            mostrarBuenaRespuesta()
            reproducirCorrecto()
        } else {
            respuestasIncorrectas++
            mostrarError()
            reproducirIncorrecto()
        }

        cont++
    }

    fun showMessage(msg:String){
        Toast.makeText(this, "Selecciona una respuesta", Toast.LENGTH_SHORT).show()
    }



}