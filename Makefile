FILES=$(addprefix bin/, $(addsuffix .class, MinHash MinHashSimilarities Preprocessor MinHashAccuracy MinHashTime LSH NearDuplicates))

TEST=$(addprefix bin/, $(addsuffix .class, MinHashTest MinHashSimilaritiesTest LSHTest PreprocessorTest NearDuplicateTest))


.PHONY: default makebin clean

default: $(FILES)
	@echo "compilation successful"

test: $(TEST) $(FILES)
	@echo "test compilation successful"

bin/%.class: src/%.java | makebin
	@echo "compiling $<"
	@javac -Werror -d bin/ -cp bin:src/ -Xlint $<

bin/%.class: test/%.java | makebin
	@echo "compiling $<"
	@javac -Werror -d bin/ -cp bin:src/:test/ -Xlint $<


clean:
	@[ ! -d bin ] || echo "removing bin directory"
	@[ ! -d bin ] || rm -r bin

makebin:
	@[  -d bin ] || echo "making bin directory"
	@[  -d bin ] || mkdir bin
