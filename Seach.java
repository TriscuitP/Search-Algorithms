public class Search 
    {
    /** Do a search from the initial state to the goal state. 
     * 
     * @param initial The state to search from 
     * @param goal The state to find
     * @param searchType The type of search to do
     * @return The final state (which will be equal to the goal state, and whose
     *         path to the original state can be found by following the parent pointers)
     */
    public static State doSearch(State initial, State goal, SearchType searchType, boolean printNumberOfExpandedStates) 
        {
        float f;
        int expandedStates = 0;
        State current = initial;
        switch(searchType)
            {
            case UNIFORMCOST:
                OpenList ol = new OpenList();
                ClosedList cl = new ClosedList();
                f = initial.gValue();
                ol.insert(initial, (int)f);
                while(!ol.isEmpty())
                    {
                    OpenList.NodeOL best = ol.removemin();
                    if(best.getState().equals(goal))
                        {
                        System.out.println("Expanded States: " + expandedStates);
                        return best.getState();
                        }
                    if(!cl.find(best.getState()))
                        {
                        cl.add(best.getState(), best.getState().hashCode());
                        expandedStates++;
                        State[] c = best.getState().getChildren();
                        for(State neighbor : c)
                            {
                            if(!cl.find(neighbor))
                                {
                                f = neighbor.gValue();
                                ol.insert(neighbor, (int)f);
                                }
                            }
                        }
                    }
                break;
            case GREEDY:
                OpenList olG = new OpenList();
                ClosedList clG = new ClosedList();
                f = initial.distanceToState(goal);
                olG.insert(initial, (int)f);
                while(!olG.isEmpty())
                    {
                    OpenList.NodeOL best = olG.removemin();
                    if(best.getState().equals(goal))
                        {
                        System.out.println("Expanded States: " + expandedStates);
                        return best.getState();
                        }
                    
                    if(!clG.find(best.getState()))
                        {
                        clG.add(best.getState(), best.getState().hashCode());
                        expandedStates++;
                        State[] c = best.getState().getChildren();
                        for(State neighbor : c)
                            {
                            if(!clG.find(neighbor))
                                {
                                f = neighbor.distanceToState(goal);
                                olG.insert(neighbor, (int)f);
                                }
                            }
                        }
                    }
                break;
            case ASTAR:
                OpenList olStar = new OpenList();
                ClosedList clStar = new ClosedList();
                f = initial.gValue() + initial.distanceToState(goal);
                olStar.insert(initial, f);
                while(!olStar.isEmpty())
                    {
                    OpenList.NodeOL curr = olStar.removemin();
                    if(curr.getState().equals(goal))
                        {
                        System.out.println("Expanded States: " + expandedStates);
                        return curr.getState();
                        }                   
                    if(!clStar.find(curr.getState()))
                        {
                        clStar.add(curr.getState(), curr.getState().hashCode());
                        expandedStates++;
                        State[] c = curr.getState().getChildren();
                        for(State neighbor : c)
                            {
                            if(!clStar.find(neighbor))
                                {                               
                                f = neighbor.gValue() + neighbor.distanceToState(goal);
                                olStar.insert(neighbor, f);
                                }
                            }
                        }
                    }
                break;
            }
        
        return null;
        }
    
    
    }





