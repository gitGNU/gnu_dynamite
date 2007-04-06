-- Quick and nasty Haskell code - Mike trying summat out

setminus :: Eq a => [a] -> [a] -> [a]
setminus xs ys = foldr without xs ys 

without :: Eq a => a -> [a] -> [a]
without _ []  = []
without y (x:xs)
  | x == y    = without y xs
  | otherwise = x : without y xs

{-
      n = names
      c = clocks
      v = variables
      e = environ names
-}


class Complemented a where
  co :: a -> a

data Visible n
  = ActIn  n
  | ActOut n
  deriving Eq

instance Complemented (Visible n) where
  co (ActIn  x) = ActOut x
  co (ActOut x) = ActIn  x

data Hidden = Tau deriving Eq
type Action n = Either Hidden (Visible n)

data Capability n e
  = In    e
  | Out   e
  | Open  e
  | OnIn  (Action n) e
  | OnOut (Action n) e
  | CoIn
  | CoOut
  | CoOpen


class EvalContext x where
  getDefn :: Eq v => x n c v e -> v -> Proc n c v e

data Eq v => Equation n c v e
  = Equation v (Proc n c v e)

instance EvalContext Equation where
  getDefn (Equation v p) v'
    | v == v'   = p
    | otherwise = error "lookup: variable not defined"


data (Eq v, Eq n, Eq e, Eq c) => Proc n c v e
  = Nil
  | Omega
  | DeltaAll
  | Delta  { clock        :: c }
  | Prefix { prefix       :: (Action n),
             continuation :: Proc n c v e }
  | Sum    { left         :: Proc n c v e,
             right        :: Proc n c v e }
  | Par    { left         :: Proc n c v e,
             right        :: Proc n c v e }
  | FTO    { try          :: Proc n c v e, 
             clock        :: c,
             catch        :: Proc n c v e }
  | STO    { try          :: Proc n c v e, 
             clock        :: c,
             catch        :: Proc n c v e }
  | Rec    { var          :: v,
             defn         :: Proc n c v e }
  | Var    { var          :: v }
  | Res    { proc         :: Proc n c v e,
             ignore       :: [n] }
  | Amb    { environ      :: e,
             proc         :: Proc n c v e,
             bouncer      :: Proc n c v e,
             clockset     :: [c] }
  | Cap    { capability   :: Capability n e,
             continuation :: Proc n c v e }

data Event n c e
  = Act    { action       :: Action n }
  | Tick   { tick         :: c }
  | Move   { move         :: Capability n e }
  | MovedIn
  | MovedOut
  | Opened

data Arrow n c v e 
  = Arrow  { source       :: Proc n c v e,
             event        :: Event n c e,
             target       :: Proc n c v e }


{-
    rules [preconditions] postcondition = True
    if and only if

                   preconditions
                   -------------
                   postcondition
-}

clocks    :: Proc n c v e -> [c]
clocks = error "not yet implemented"

actions   :: Proc n c v e -> [Action n]
actions = error "not yet implemented"

fn        :: Proc n c v e -> [n]
fn = error "not yet implemented"

environs  :: Proc n c v e -> [e]
environs = error "not yet implemented"

variables :: Proc n c v e -> [v]
variables = error "not yet implemented"

actions1 :: (Eq n, Eq v, Eq c, Eq e) => Equation n c v e -> Proc n c v e -> [Action n]
actions1 es p = case p of
  Nil             -> []
  Omega           -> []
  DeltaAll        -> []
  Delta s         -> []
  Prefix a p      -> [a]
  Sum p q         -> actions1 es p ++ actions1 es q
  Par p q         -> actions1 es p ++ actions1 es q
  FTO p s q       -> error "not yet implemented"
  STO p s q       -> error "not yet implemented"
  Res p i         -> (actions1 es p) `setminus` (map (Right . ActIn) i) `setminus` (map (Right . ActOut) i)
  Amb e p b c     -> error "not yet implemented"
  Cap c p         -> []
  Var v           -> actions1 es (getDefn es v)
  Rec v p@(Var w) -> if (v == w) then [] else actions1 es p
  Rec v p         -> actions1 es p


           
reduce :: Proc n c v e -> [Proc n c v e]
reduce _ = error "reduce: not yet implemented"

reduceAll :: Proc n c v e -> [Proc n c v e]
reduceAll p = foldr (++) [] (map reduceAll (reduce p))



