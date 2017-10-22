# Huffman-Heap
Huffman en/decoding using 4-way heap

This is an implementation of Huffman coding using a 4-way heap as the underlying priority queue.
The program first counts the frequencies of the words in the given input file.
A Huffman tree and code table are then generated using this data. 
These are then used to encode the input file. 

Similarly, for the decoding, the code table and the encoded file is provided.
The corresponding Huffman tree is generated using the code table in memory.
This is then used to decode the given input encoded file and save it.
