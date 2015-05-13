package jewel_runner.transcriber

import edu.cmu.sphinx.api._
import edu.cmu.sphinx.linguist.dictionary.Pronunciation
import edu.cmu.sphinx.result.WordResult
import java.io.InputStream

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
object SphinxTranscriberDemo {

  def main(args : Array[String]){
    /*
    // edu.cmu.sphinx.api.Configuration
    // Configuration options for speech recognition
    */
    val configuration = new Configuration

    /*
    // Set path to acoustic model
    // Feature vectors that map raw audio to phones
    // An acoustic model maps to phones (or triphones)
    //
    // What is a triphone?
    // first part of the phone depends on its preceding phone,
    // the middle part is stable, and the next part depends on the subsequent phone.
    // That's why there are often three states in a phone selected for speech recognition.
    // For example “u with left phone b and right phone d” in the word “bad”
    //
    // Usually we use 4000 distinct short sound detectors to compose detectors for triphones
    */
    configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us")

    /*
    // Set path to dictionary.
    // A dictionary maps phones to words
    // from cmudict-en-us.dict
       elvis EH L V IH S
       elvis' EH L V IH S
       elvis's EH L V IH S IH Z
       elvy EH L V IY
       elway EH L W EY
     */
    configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict")

    /*
    // Set language model.
    // http://cmusphinx.sourceforge.net/doc/sphinx4/edu/cmu/sphinx/result/Lattice.html
    //
    // Graph that contains statistics of word sequences. It limits the search space by
    // predicting the next word given prior words

             "a"           "cat"
           /     \        /     \
       <s>          "big"         - </s>
           \     /        \     /
            "all"          "dog"
     */
    configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp")

    val stream : InputStream = getClass.getResourceAsStream("/10001-90210-01803.wav")
    stream.skip(44)

    // initialize the recognizer
    val recognizer : StreamSpeechRecognizer = new StreamSpeechRecognizer(configuration)

    // Simple recognition with generic model
    // From: http://cmusphinx.sourceforge.net/wiki/tutorialconcepts
    recognizer.startRecognition(stream)

    /*
    // Decode audio (Recognizer.decode, Decoder.decode) -> Extract features (FrontEnd.getData),
    // find phones, search language model (searchManager.recognize())
    // calls Recognizer.decode ->  ->
    // Features:
    // http://cmusphinx.sourceforge.net/doc/sphinx4/edu/cmu/sphinx/frontend/FrontEnd.html
    */
    var result : SpeechResult = recognizer.getResult()

    while (result != null) {

      printf("Hypothesis: %s\n", result.getHypothesis())
      println("List of recognized words and their times:")

      for (r: WordResult <- result.getWords()) {
        printf("Wordresult: %s\n", r)
        printf("Pronunciation: %s\n", r.getPronunciation.getUnits.map(x => x.toString))
      }

      println("Best 3 hypothesis:")
      for (s : String <- result.getNbest(3))
        println(s)

      println("calling recognizer.getResult()")
      result = recognizer.getResult()
    }
    recognizer.stopRecognition()
  }
}
