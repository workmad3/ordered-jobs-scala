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
    orderedJobs.parse("a =>\nb => c\nc =>") must containInOrder(List("a", "c", "b"))
  }

  "string 'a =>\nb => c\nc => f\nd => a\ne => b\nf =>' should follow correct dependencies" in {
    orderedJobs.parse("a =>\nb => c\nc => f\nd => a\ne => b\nf =>") must containInOrder(List("a", "f", "c", "b", "d", "e"))
  }

  "string 'a =>\nb =>\nc => c' should raise an error saying 'Jobs can't depend on themselves'" in {
    orderedJobs.parse("a =>\nb =>\nc => c") must throwA[JobSelfReferenceException]
  }

  "string 'a =>\nb => c\nc => f\nd => a\ne =>\nf => b' should raise an error saying 'Jobs can't have circular dependencies'" in {
    orderedJobs.parse("a =>\nb => c\nc => f\nd => a\ne =>\nf => b") must throwA[JobCircularDependencyException]
  }
}
