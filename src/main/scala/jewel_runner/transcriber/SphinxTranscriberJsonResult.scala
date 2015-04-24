package jewel_runner.transcriber

import java.io.FileInputStream

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.SerializationFeature

import edu.cmu.sphinx.api._
import edu.cmu.sphinx.result.WordResult
import edu.cmu.sphinx.util.LogMath

// TODO: What is this??
import scala.collection.JavaConversions._

/*
 SpeechResult - http://cmusphinx.sourceforge.net/doc/sphinx4/edu/cmu/sphinx/api/SpeechResult.html
 WordResult -  http://cmusphinx.sourceforge.net/doc/sphinx4/edu/cmu/sphinx/result/WordResult.html
 */

/**
 * Created by telvis on 3/31/15.
 */
object SphinxTranscriberJsonResult {
  // For JSON parsing
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  mapper.enable(SerializationFeature.INDENT_OUTPUT)

  def to_json_string(m : scala.collection.mutable.HashMap[String, Any]): String = {
    mapper.writeValueAsString(m)
  }

  def main(args : Array[String]){

    if (args.length == 0){
      System.err.println("Number of args: "+args.length.toString)
      System.err.println("Usage : PROG <path_to_file> [sample_rate]")
      System.exit(1)
    }

    val inputFile = args(0)
    var sample_rate = 8000
    if (args.length == 2){
      sample_rate = args(1).toInt
    }

    /* configuration */
    val configuration = new Configuration
    configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us")
    configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict")
    configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp")
    configuration.setSampleRate(sample_rate)

    /* Open the file */
    val stream = new FileInputStream(inputFile)

    // The header of a WAV (RIFF) file is 44 bytes long
    // http://www.topherlee.com/software/pcm-tut-wavformat.html
    stream.skip(44)

    // initialize the recognizer
    val recognizer : StreamSpeechRecognizer = new StreamSpeechRecognizer(configuration)

    // Simple recognition with generic model
    recognizer.startRecognition(stream)

    /* Get the first result */
    var result : SpeechResult = recognizer.getResult()

    while (result != null) {
      // Sometimes the result can be non-null but the results are still empty
      if (result.getHypothesis.length > 0){
        val speech_record = new scala.collection.mutable.HashMap[String, Any]
        speech_record("hypothesis") = result.getHypothesis
        println("List of recognized words and their times:")

        val word_results = result.getWords

        // Try to handle unexpected null values
        speech_record("words") = word_results match {
          case _ if (word_results == null) => Map()
          case _ => word_results.map({ r  => {
            Map("word" -> r.getWord.toString,
              "start" -> r.getTimeFrame.getStart.toString,
              "end" -> r.getTimeFrame.getEnd.toString,
              "confidence_log" -> LogMath.getLogMath.logToLinear(r.getConfidence.toFloat))
          }
          })
        }

        speech_record("n_best") = result.getNbest(3)
        println(to_json_string(speech_record))
        println("calling recognizer.getResult()")
      }

      // Get the next result
      result = recognizer.getResult()
    }
    recognizer.stopRecognition()
  }
}

