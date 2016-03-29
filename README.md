# twitter-streaming-example
Trying out the Twitter Streaming API

Filters tweets by keyword and stores them for stream/batch processing.

In this example, we would like to know of SF or LA is more concerned with saving
when they tweet about money. First we filter tweets that contain the word money,
then identify 2 categories SF and LA. Then we ask how many of each category
contain the word save. Finally we perform a rolling Fishers Exact Test to
determine the likelihood that any observed differences are due to the effects of
chance rather than the effects of a true difference in behavior. Ideally, we
would be able see if there were certain times of day where LA and SF populations
were distinct in their interest in saving, and thus better target services.

Note: Fisher's Exact test is picky when it comes to data sizes: with too little
data, any of the observations could have happened by chance, and with too much
data, the calculation gets huge. Tuning the sample rate to can be automated in
order to accommodate this.
