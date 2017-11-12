* work on vertical and horizontals lines surrounding given field
* returning after first safe move found

**If a number is touching the same number of squares, then the squares are all mines.**

1. get 5x5
2. If virgin, then:
	3. for all surrounding 3x3, calculate basic_pattern
	4. If basic_pattern for any, mark as mine = non safe move
	5. Else, begin checking more complicated patterns