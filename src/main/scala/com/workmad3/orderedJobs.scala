package com.workmad3

object orderedJobs {
  def parse(jobSpec: String):List[String] = {
    var jobList = new JobList
    jobSpec.lines.foreach(jobList.createJob(_))
    jobList.toList
  }
}

class JobList {
  var jobs = List[Job]();

  def createJob(job: String) = {
    val spec = job.split("=>").map(_.stripPrefix(" ").stripSuffix(" "))
    if (spec.size > 1) {
      addDependency(new Job(spec(0)), new Job(spec(1)))
    }
    else
      addJob(new Job(spec(0)))
  }

  def addJob(job: Job) = {
    if (!jobExists(job))
      jobs = jobs :+ job
  }

  def addDependency(job: Job, dependency: Job) = {
    if (job.name == dependency.name) {
      throw new JobSelfReferenceException
    }
    else if (jobExists(job) && jobExists(dependency)) {
      throw new JobCircularDependencyException
    }
    else if (jobExists(job)) {
      addJobBefore(dependency, job)
    } else {
      addJob(dependency)
      addJob(job)
    }
  }

  def addJobBefore(job: Job, otherJob: Job) = {
    val i = jobs.indexWhere(_.name == otherJob.name)
    jobs = (jobs.take(i)) ::: job :: (jobs.drop(i))
  }

  def jobExists(job: Job):Boolean = {
    jobs.exists(_.name == job.name)
  }

  def toList():List[String] = jobs.map(_.toString)
}

class Job(jobName: String) {
  val name = jobName

  override def toString = name
}

class JobSelfReferenceException extends Exception {
}

class JobCircularDependencyException extends Exception {
}
