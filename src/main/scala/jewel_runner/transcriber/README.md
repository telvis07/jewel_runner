## SphinxTranscriberDemo

How to run the transcriber demo
    
    sbt package
    export JAVA_OPTS="-Xmx2048m -Xms256m"
    scala -cp target/scala-2.11/jewel_runner-assembly-1.0.jar jewel_runner.transcriber.SphinxTranscriberDemo

Example Output:

    {
      "hypothesis" : "nine oh two one oh",
      "words" : [ {
        "word" : "<sil>",
        "start" : "2830",
        "end" : "3920",
        "confidence_log" : 0.9164845116313057
      }, {
        "word" : "nine",
        "start" : "3930",
        "end" : "4140",
        "confidence_log" : 0.9168512191279446
      }, {
        "word" : "oh",
        "start" : "4150",
        "end" : "4250",
        "confidence_log" : 0.9175850812019422
      }, {
        "word" : "two",
        "start" : "4260",
        "end" : "4490",
        "confidence_log" : 1.0
      }, {
        "word" : "one",
        "start" : "4500",
        "end" : "4650",
        "confidence_log" : 1.0
      }, {
        "word" : "oh",
        "start" : "4660",
        "end" : "4890",
        "confidence_log" : 0.999400110419094
      }, {
        "word" : "<sil>",
        "start" : "4900",
        "end" : "5030",
        "confidence_log" : 0.9979230125498715
      } ],
      "n_best" : [ "<s> bad oh two one oh </s>", "<s> nine oh two one oh </s>", "<s> that oh two one oh </s>" ]
    }
    
    