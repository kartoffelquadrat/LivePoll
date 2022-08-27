//package eu.kartoffelquadrat.livepoll.controllers;
//
//import eu.kartoffelquadrat.livepoll.Poll;
//
///**
// * Transfer object to communicate render data to frontend.
// */
//public class PollRenderData extends Poll {
//
//  private final String[] qrlocations;
//
//  /**
//   * Constructor. Sets up topic and options/vote-amount map.
//   *
//   * @param topic       as the final topic for this poll.
//   * @param options     as the string options for all possible answers.
//   * @param qrlocations as the string locations of the qr code locations on file system for all
//   *                    options.
//   */
//  public PollRenderData(String topic, String[] options, String[] qrlocations) {
//    super(topic, options);
//    this.qrlocations = qrlocations;
//  }
//
//  /**
//   * Getter for qr locations.
//   *
//   * @return string array of all qr locations on disk in same order as vote options.
//   */
//  public String[] getQrlocations() {
//    return qrlocations;
//  }
//}
