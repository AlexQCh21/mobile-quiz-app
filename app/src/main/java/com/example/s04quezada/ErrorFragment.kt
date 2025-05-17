package com.example.s04quezada

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ErrorFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var respuestaCorrecta: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            respuestaCorrecta = it.getString(ARG_RESPUESTA_CORRECTA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false)
    }

    // Aquí sí puedes usar view.findViewById
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNext = view.findViewById<Button>(R.id.btn_continuar_error)
        val tvRespuesta = view.findViewById<TextView>(R.id.tvRespuesta)

        tvRespuesta.text = "Respuesta: $respuestaCorrecta"

        btnNext.setOnClickListener {
            // Puedes hacer algo aquí o llamar un método de la Activity
            (activity as? MainActivity)?.mostrarPregunta()
        }
    }

    companion object {
        private const val ARG_RESPUESTA_CORRECTA = "respuesta_correcta"

        @JvmStatic
        fun newInstance(respuestaCorrecta: String) =
            ErrorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_RESPUESTA_CORRECTA, respuestaCorrecta)
                }
            }
    }
}