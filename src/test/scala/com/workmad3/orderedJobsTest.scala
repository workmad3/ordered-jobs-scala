package com.workmad3
import org.specs._

class orderedJobsTest extends SpecificationWithJUnit {
  "empty string should contain no jobs" in {
    orderedJobs.parse("").size must be equalTo(0);
  }

  "string 'a =>' should contain the job 'a'" in {
    orderedJobs.parse("a =>") must haveTheSameElementsAs(List("a"))
  }

  "string 'a =>\nb =>\nc =>' should contain the jobs 'a', 'b' and 'c'" in {
    orderedJobs.parse("a =>\nb =>\nc =>") must haveTheSameElementsAs(List("a", "b", "c"))
  }

  "string 'a =>\nb => c\n c=>' should contain the jobs 'a', 'b' and 'c'" in {
    orderedJobs.parse("a =>\nb => c\nc =>") must haveTheSameElementsAs(List("a", "b", "c"))
  }

  "string 'a =>\nb => c\n c=>' should contain the job 'c' before the job 'b'" in {
    orderedJobs.parse("a =>\nb => c\nc =>") must containInOrder(List("c", "b"))
  }
}
