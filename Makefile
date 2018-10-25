FILES=$(addprefix src/, $(addsuffix .class, MinHash MinHashSimilarity Preprocessing MinHashAccuracy MinHashTime LSH NearDuplicates))

TEST=$(addprefix test/, $(addsuffix .class, a))


.PHONY: default makebin clean

default: $(FILES)
	@echo "compilation successful"

test: $(TEST)
	@echo "test compilation successful"

bin/%.class: src/%.java | makebin
	@echo "compiling $<"
	@javac -Werror -d bin/ -cp bin:src/:test/ -Xlint $<


clean:
	@[ ! -d bin ] || echo "removing bin directory"
	@[ ! -d bin ] || rm -r bin

makebin:
	@[  -d bin ] || echo "making bin directory"
	@[  -d bin ] || mkdir bin
