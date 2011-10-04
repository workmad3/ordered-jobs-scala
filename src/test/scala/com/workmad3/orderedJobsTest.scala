package com.workmad3
import org.specs._

class orderedJobsTest extends SpecificationWithJUnit {
  "empty string should contain no jobs" in {
    orderedJobs.parse("").size must be equalTo(0);
  }

  "string 'a =>' should contain the job 'a'" in {
    orderedJobs.parse("a =>") must haveTheSameElementsAs(List("a"))
  }
}
