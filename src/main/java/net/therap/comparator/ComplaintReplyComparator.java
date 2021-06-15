package net.therap.comparator;

import net.therap.model.ComplaintReply;

import java.util.Comparator;

/**
 * @author aditya.chakma
 * @since 6/11/21
 */
public class ComplaintReplyComparator implements Comparator<ComplaintReply> {

    @Override
    public int compare(ComplaintReply complaintReply, ComplaintReply t1) {
       return complaintReply.getId() >= t1.getId() ? 1 : -1;
    }
}
