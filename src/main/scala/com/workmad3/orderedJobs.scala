package com.workmad3

object orderedJobs {
  def parse(jobSpec: String):List[String] = {
    if (jobSpec.compareTo("") == 0)
      List[String]()
    else {
      var jobList = new JobList
      jobSpec.lines.foreach(jobList.createJob(_))
      jobList.toList
    }
  }
}

class JobList {
  var jobs = List[Job]();

  def createJob(job: String) = {
    val spec = job.split("=>").map(_.stripPrefix(" ").stripSuffix(" "))
    if (spec.size > 1) {
      addJob(new Job(spec(1)))
      addJob(new Job(spec(0)))
    }
    else
      addJob(new Job(spec(0)))
  }

  def addJob(job: Job) = {
    if (!jobs.exists(_.name == job.name))
      jobs = jobs :+ job
  }

  def toList():List[String] = jobs.map(_.toString)
}

class Job(jobName: String) {
  val name = jobName

  override def toString = name
}
