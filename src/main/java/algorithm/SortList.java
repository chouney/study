package algorithm;

/**
 * Created by manatea on 2016/11/30.
 */
public class SortList {
    public  class ListNode {
             int val;
             ListNode next;
             ListNode(int x) { val = x; }
         }
    public ListNode merge(ListNode p1,ListNode p2){
        ListNode prev = null;
        if(p1==null || p2==null){
            return null;
        }
        ListNode hNode = p1.val < p2.val ? p2 : p1;
        ListNode lNode = p1.val < p2.val ? p1 : p2;
        ListNode head = lNode;
        prev = lNode;
        while(lNode!=null&&hNode!=null){
            if(lNode.val>hNode.val){
                ListNode p3 = hNode.next;
                hNode.next=lNode;
                prev.next=hNode;
                prev=hNode;
                hNode = p3;
            }else {
                prev = lNode;
                lNode = lNode.next;
            }
        }
        while(hNode!=null){
            prev.next=hNode;
            prev = hNode;
            hNode = hNode.next;
        }
        return head;
    }

    public ListNode sortList(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode p1 = head;
        ListNode p2 = head;
        ListNode prev = null;
        while(p2!=null&&p2.next!=null){
            prev = p1;
            p1=p1.next;
            p2=p2.next.next;
        }
        prev.next = null;
        head = sortList(head);
        p1 = sortList(p1);

        return merge(head,p1);
    }
    public static void main(String[] args){
       int a[] = {3,7,1,2,9,6,3,8};
        SortList t = new SortList();
     ListNode l = t.new ListNode(3);
        ListNode head = l;
       for(int i=1;i<a.length;i++){
           ListNode b = t.new ListNode(a[i]);
           l.next = b;
           l=l.next;
       }
        head = t.sortList(head);

   }
}
