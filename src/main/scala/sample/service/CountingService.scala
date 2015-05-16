package sample.service

import org.springframework.stereotype.Service

/**
 * A simple service that can increment a number.
 */

@Service
class CountingService {
  /**
   * Increment the given number by one.
   */
  def increment(count: Int) = count + 1
}
