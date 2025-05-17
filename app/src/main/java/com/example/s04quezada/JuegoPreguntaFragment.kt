package com.example.s04quezada

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class JuegoPreguntaFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    lateinit var radioGroup: RadioGroup
    lateinit var tvPregunta:TextView

    var textoPregunta: String? = null
    var opcionesString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            textoPregunta = it.getString(ARG_PARAM1)
            opcionesString = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_juego_pregunta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        radioGroup = view.findViewById(R.id.rdOpciones)
        tvPregunta = view.findViewById(R.id.tvPregunta)
        val btnNext = view.findViewById<Button>(R.id.btn_next_pregunta)

        tvPregunta.text = textoPregunta
        val opciones = opcionesString?.split("||") ?: listOf()
        agregarRadioButtons(opciones.size == 2, opciones)


        btnNext.setOnClickListener {
            if(radioGroup.checkedRadioButtonId != -1){
                (activity as? MainActivity)?.calificarRespuesta(obtenerRespuestaSeleccionada())
            }

        }
    }

    fun mostrarPregunta(pregunta:String?=""){
        tvPregunta.text = pregunta
    }

    fun obtenerRespuestaSeleccionada(): String? {
        val selectedId = radioGroup.checkedRadioButtonId
        return if (selectedId != -1) {
            view?.findViewById<RadioButton>(selectedId)?.text?.toString()
        } else {
            null
        }
    }



    fun agregarRadioButtons(isTrueOrFalse: Boolean, opciones: List<String>? = null) {
        radioGroup.removeAllViews()

        val opcionesAUsar = if (isTrueOrFalse) listOf("Verdadero", "Falso")
        else opciones ?: listOf("2 Componentes", "3 Componentes", "4 Componentes", "5 Componentes")

        for (opcion in opcionesAUsar) {
            val radioButton = RadioButton(requireContext()).apply {
                text = opcion
                setTextColor(ContextCompat.getColor(requireContext(), R.color.radiobtnTextcolor))
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f)
                setTypeface(null, Typeface.BOLD)
                setPadding(8.dpToPx(), 8.dpToPx(), 8.dpToPx(), 8.dpToPx())
                setBackgroundResource(R.drawable.radiobutton_background)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 10.dpToPx(), 0, 15.dpToPx())
                }
            }
            radioGroup.addView(radioButton)
        }
    }

    private fun Int.dpToPx(): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics
    ).toInt()

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JuegoPreguntaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
