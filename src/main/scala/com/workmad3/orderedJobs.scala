package com.workmad3

object orderedJobs {
  def parse(jobSpec: String):List[String] = {
    if (jobSpec.compareTo("") == 0)
      List[String]()
    else
      List("a")
  }
}
