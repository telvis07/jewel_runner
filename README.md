An example to use [CMUSphinx speech transcriber](http://cmusphinx.sourceforge.net/doc/sphinx4/edu/cmu/sphinx/api/StreamSpeechRecognizer.html) in Scala and
print results in JSON format.

### References

The following references were useful to me for this work.

- [Scala for the Impatient](http://www.horstmann.com/scala/index.html)
- [sbt](http://www.scala-sbt.org/documentation.html)
- [scala docs](http://www.scala-lang.org/api/current/index.html#package)
- [sphinx](http://cmusphinx.sourceforge.net/wiki/)
- [How CMUSphinx converts raw audio into text](http://cmusphinx.sourceforge.net/wiki/tutorialconcepts)

## SphinxTranscriberJsonResult

How to run the transcriber demo
    
    sbt package
    export JAVA_OPTS="-Xmx2048m -Xms256m"
    scala -cp target/scala-2.11/jewel_runner-assembly-1.0.jar jewel_runner.transcriber.SphinxTranscriberDemo


To transcribe the example audio file

    export JAVA_OPTS="-Xmx2048m -Xms256m"

    scala -cp target/scala-2.11/jewel_runner-assembly-1.0.jar \
      jewel_runner.transcriber.SphinxTranscriberJsonResult \
      src/main/resources/10001-90210-01803.wav 16000

Example JSON Output

    {
      "hypothesis": "cyril one eight zero three",
      "n_best": [
        "<s> zero one eight zero three </s>",
        "<s> cyril one eight sir oh three </s>",
        "<s> cyril one eight zero three </s>"
      ],
      "words": [
        {
          "start": "5030",
          "pronunciation": "*SIL",
          "end": "6240",
          "confidence_log": 0.999500067009584,
          "word": "<sil>"
        },
        {
          "start": "6250",
          "pronunciation": "S IH R AH L",
          "end": "6680",
          "confidence_log": 1.0,
          "word": "cyril"
        },
        {
          "start": "6690",
          "pronunciation": "W AH N",
          "end": "6860",
          "confidence_log": 0.9992002272260668,
          "word": "one"
        },
        {
          "start": "6870",
          "pronunciation": "EY T",
          "end": "7090",
          "confidence_log": 0.9984010940886235,
          "word": "eight"
        },
        {
          "start": "7100",
          "pronunciation": "Z IH R OW",
          "end": "7460",
          "confidence_log": 0.9992002272260668,
          "word": "zero"
        },
        {
          "start": "7470",
          "pronunciation": "TH R IY",
          "end": "7900",
          "confidence_log": 1.0,
          "word": "three"
        }
      ], 
      "filename": "src/main/resources/10001-90210-01803.wav"
    }
