Feature: Get links by crawling

  Scenario: User gets empty list when navigating to a simple page that does not have any links
    Given a url "http://www.brainjar.com/java/host/test.html" that is reachable by http
    When a user calls crawll the web api with url "http://www.brainjar.com/java/host/test.html" as query parameter
    Then the status code is 200
    And response is empty list for images and links

  Scenario: User gets valid crawl list when navigating to a  page with internal and external links
    Given a url "http://wiprodigital.com" that is reachable by http
    When a user calls crawll the web api with url "http://wiprodigital.com" as query parameter
    Then the status code is 200
    And response is valid list of images and links